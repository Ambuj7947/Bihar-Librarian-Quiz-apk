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
    fun `verify syllabus units and empty initial questions for daily additions`() {
        val categories = DefaultQuestions.allCategories
        assertEquals(6, categories.size)
        assertTrue(categories.contains(DefaultQuestions.UNIT_1))
        assertTrue(categories.contains(DefaultQuestions.UNIT_2))
        assertTrue(categories.contains(DefaultQuestions.UNIT_3))
        assertTrue(categories.contains(DefaultQuestions.UNIT_4))
        assertTrue(categories.contains(DefaultQuestions.UNIT_5))
        assertTrue(categories.contains(DefaultQuestions.UNIT_6))
        assertTrue(DefaultQuestions.isExtraQuestionsUnit(DefaultQuestions.UNIT_6))
        org.junit.Assert.assertFalse(DefaultQuestions.isExtraQuestionsUnit(DefaultQuestions.UNIT_1))

        val questions = DefaultQuestions.getInitialQuestions()
        assertTrue("Initial questions should be empty ready for daily user additions", questions.isEmpty())
    }
}
