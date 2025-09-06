package com.example.yandexsummerschool.repositories

import com.example.yandexsummerschool.data.repositories.executeWithRetries
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import retrofit2.Response

class ExecuteWithRetriesTest {

    @Test
    fun `executeWIthRetries with successful response on first attempt should return success immediately`() = runTest {
        // Given
        val successResponse = Response.success("Success")
        var callCount = 0

        val functionBlock: suspend () -> Response<String> = {
            callCount++
            successResponse
        }

        // When
        val result = executeWithRetries(functionBlock)

        // Then
        assertEquals(1, callCount)
        assertTrue(result.isSuccessful)
        assertEquals("Success", result.body())
    }

    @Test
    fun `executeWIthRetries with 500 error should retry and eventually succeed`() = runTest {
        // Given
        val errorResponse = Response.error<String>(500, okhttp3.ResponseBody.create(null, "Server Error"))
        val successResponse = Response.success("Success after retry")
        var callCount = 0

        val functionBlock: suspend () -> Response<String> = {
            callCount++
            if (callCount <= 2) errorResponse else successResponse
        }

        // When
        val result = executeWithRetries(functionBlock)

        // Then
        assertEquals(3, callCount) // Should retry twice and succeed on third attempt
        assertTrue(result.isSuccessful)
        assertEquals("Success after retry", result.body())
    }

    @Test
    fun `executeWIthRetries with persistent 500 errors should return last error after max retries`() = runTest {
        // Given
        val errorResponse = Response.error<String>(500, okhttp3.ResponseBody.create(null, "Server Error"))
        var callCount = 0

        val functionBlock: suspend () -> Response<String> = {
            callCount++
            errorResponse
        }

        // When
        val result = executeWithRetries(functionBlock)

        // Then
        assertEquals(3, callCount) // MAX_CONNECTION_RETRIES = 3
        assertFalse(result.isSuccessful)
        assertEquals(500, result.code())
    }

    @Test
    fun `executeWIthRetries with 400 error should not retry and return immediately`() = runTest {
        // Given
        val errorResponse = Response.error<String>(400, okhttp3.ResponseBody.create(null, "Bad Request"))
        var callCount = 0

        val functionBlock: suspend () -> Response<String> = {
            callCount++
            errorResponse
        }

        // When
        val result = executeWithRetries(functionBlock)

        // Then
        assertEquals(1, callCount) // Should not retry for 4xx errors
        assertFalse(result.isSuccessful)
        assertEquals(400, result.code())
    }

    @Test
    fun `executeWIthRetries with 600 error should not retry and return immediately`() = runTest {
        // Given
        val errorResponse = Response.error<String>(600, okhttp3.ResponseBody.create(null, "Custom Error"))
        var callCount = 0

        val functionBlock: suspend () -> Response<String> = {
            callCount++
            errorResponse
        }

        // When
        val result = executeWithRetries(functionBlock)

        // Then
        assertEquals(1, callCount) // Should not retry for 6xx errors
        assertFalse(result.isSuccessful)
        assertEquals(600, result.code())
    }

    @Test
    fun `executeWIthRetries should retry only for 5xx errors`() = runTest {
        // Given
        val errorResponse = Response.error<String>(503, okhttp3.ResponseBody.create(null, "Service Unavailable"))
        var callCount = 0

        val functionBlock: suspend () -> Response<String> = {
            callCount++
            errorResponse
        }

        // When
        val result = executeWithRetries(functionBlock)

        // Then
        assertEquals(3, callCount) // Should retry for 5xx errors
        assertFalse(result.isSuccessful)
        assertEquals(503, result.code())
    }

    @Test
    fun `executeWIthRetries should handle network timeout errors`() = runTest {
        // Given
        val timeoutResponse = Response.error<String>(504, okhttp3.ResponseBody.create(null, "Gateway Timeout"))
        var callCount = 0

        val functionBlock: suspend () -> Response<String> = {
            callCount++
            timeoutResponse
        }

        // When
        val result = executeWithRetries(functionBlock)

        // Then
        assertEquals(3, callCount) // Should retry for 504 (5xx error)
        assertFalse(result.isSuccessful)
        assertEquals(504, result.code())
    }

    @Test
    fun `executeWIthRetries should handle service unavailable errors`() = runTest {
        // Given
        val unavailableResponse = Response.error<String>(503, okhttp3.ResponseBody.create(null, "Service Unavailable"))
        var callCount = 0

        val functionBlock: suspend () -> Response<String> = {
            callCount++
            unavailableResponse
        }

        // When
        val result = executeWithRetries(functionBlock)

        // Then
        assertEquals(3, callCount) // Should retry for 503 (5xx error)
        assertFalse(result.isSuccessful)
        assertEquals(503, result.code())
    }

    @Test
    fun `executeWIthRetries should handle internal server errors`() = runTest {
        // Given
        val internalErrorResponse =
            Response.error<String>(500, okhttp3.ResponseBody.create(null, "Internal Server Error"))
        var callCount = 0

        val functionBlock: suspend () -> Response<String> = {
            callCount++
            internalErrorResponse
        }

        // When
        val result = executeWithRetries(functionBlock)

        // Then
        assertEquals(3, callCount) // Should retry for 500 (5xx error)
        assertFalse(result.isSuccessful)
        assertEquals(500, result.code())
    }

    @Test
    fun `executeWIthRetries should handle bad gateway errors`() = runTest {
        // Given
        val badGatewayResponse = Response.error<String>(502, okhttp3.ResponseBody.create(null, "Bad Gateway"))
        var callCount = 0

        val functionBlock: suspend () -> Response<String> = {
            callCount++
            badGatewayResponse
        }

        // When
        val result = executeWithRetries(functionBlock)

        // Then
        assertEquals(3, callCount) // Should retry for 502 (5xx error)
        assertFalse(result.isSuccessful)
        assertEquals(502, result.code())
    }

    @Test
    fun `executeWIthRetries should handle http version not supported errors`() = runTest {
        // Given
        val versionNotSupportedResponse =
            Response.error<String>(505, okhttp3.ResponseBody.create(null, "HTTP Version Not Supported"))
        var callCount = 0

        val functionBlock: suspend () -> Response<String> = {
            callCount++
            versionNotSupportedResponse
        }

        // When
        val result = executeWithRetries(functionBlock)

        // Then
        assertEquals(3, callCount) // Should retry for 505 (5xx error)
        assertFalse(result.isSuccessful)
        assertEquals(505, result.code())
    }

    @Test
    fun `executeWIthRetries should not retry for 599 error`() = runTest {
        // Given
        val error599 = Response.error<String>(599, okhttp3.ResponseBody.create(null, "Network Timeout"))
        var callCount = 0

        val functionBlock: suspend () -> Response<String> = {
            callCount++
            error599
        }

        // When
        val result = executeWithRetries(functionBlock)

        // Then
        assertEquals(3, callCount) // Should retry for 599 (5xx error)
        assertFalse(result.isSuccessful)
        assertEquals(599, result.code())
    }

    @Test
    fun `executeWIthRetries should work with different response types`() = runTest {
        // Given
        val successResponse = Response.success(listOf("item1", "item2"))
        var callCount = 0

        val functionBlock: suspend () -> Response<List<String>> = {
            callCount++
            successResponse
        }

        // When
        val result = executeWithRetries(functionBlock)

        // Then
        assertEquals(1, callCount)
        assertTrue(result.isSuccessful)
        assertEquals(listOf("item1", "item2"), result.body())
    }
}
