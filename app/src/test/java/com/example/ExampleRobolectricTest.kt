package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.DefaultQuestions
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

    @Test
    fun `read string from context`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val appName = context.getString(R.string.app_name)
        assertEquals("बिहार लाइब्रेरियन क्विज़", appName)
    }

    @Test
    fun `verify initial questions have valid content and explanations`() {
        val questions = DefaultQuestions.getInitialQuestions()
        assertTrue("Initial questions should not be empty", questions.isNotEmpty())
        for (q in questions) {
            assertTrue("Question text should not be blank", q.questionHindi.isNotBlank())
            assertTrue("Explanation should not be blank", q.explanationHindi.isNotBlank())
            assertTrue("Correct option should be 1..4", q.correctOption in 1..4)
            assertTrue("Category should not be blank", q.category.isNotBlank())
        }
    }
}
