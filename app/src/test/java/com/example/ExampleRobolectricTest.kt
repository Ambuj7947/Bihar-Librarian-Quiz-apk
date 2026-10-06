package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.DefaultQuestions
import com.example.data.SubtopicRepository
import com.example.data.UserProgressStore
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
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
    fun `verify syllabus units notes and DPP questions across all units`() {
        val categories = DefaultQuestions.allCategories
        assertEquals(6, categories.size)
        assertTrue(categories.contains(DefaultQuestions.UNIT_1))
        assertTrue(categories.contains(DefaultQuestions.UNIT_2))
        assertTrue(categories.contains(DefaultQuestions.UNIT_3))
        assertTrue(categories.contains(DefaultQuestions.UNIT_4))
        assertTrue(categories.contains(DefaultQuestions.UNIT_5))
        assertTrue(categories.contains(DefaultQuestions.UNIT_6))

        val allQuestions = DefaultQuestions.getInitialQuestions()
        assertEquals("Initial questions include Unit 1 (55) + Units 2-5 (100) + Unit 6 (175) = 330", 330, allQuestions.size)

        // Verify Unit 1 subtopics (Notes & DPP)
        val unit1Subtopics = SubtopicRepository.getSubtopicsForCategory(
            DefaultQuestions.UNIT_1,
            allQuestions,
            emptyList()
        )
        assertEquals("Unit 1 must have 5 subtopics", 5, unit1Subtopics.size)
        unit1Subtopics.forEach { sub ->
            assertTrue("Subtopic notes must not be blank", sub.notesContent.isNotBlank())
            assertTrue("Subtopic questions must be available", sub.questions.isNotEmpty())
        }

        // Verify Unit 2 subtopics (Notes & DPP)
        val unit2Subtopics = SubtopicRepository.getSubtopicsForCategory(
            DefaultQuestions.UNIT_2,
            allQuestions,
            emptyList()
        )
        assertEquals("Unit 2 must have 5 subtopics", 5, unit2Subtopics.size)
        unit2Subtopics.forEach { sub ->
            assertTrue("Unit 2 notes must not be blank", sub.notesContent.isNotBlank())
            assertEquals("Unit 2 each subtopic must have 5 DPP questions", 5, sub.questions.size)
        }

        // Verify Unit 3 subtopics (Notes & DPP)
        val unit3Subtopics = SubtopicRepository.getSubtopicsForCategory(
            DefaultQuestions.UNIT_3,
            allQuestions,
            emptyList()
        )
        assertEquals("Unit 3 must have 5 subtopics", 5, unit3Subtopics.size)

        // Verify Unit 4 subtopics (Notes & DPP)
        val unit4Subtopics = SubtopicRepository.getSubtopicsForCategory(
            DefaultQuestions.UNIT_4,
            allQuestions,
            emptyList()
        )
        assertEquals("Unit 4 must have 5 subtopics", 5, unit4Subtopics.size)

        // Verify Unit 5 subtopics (Notes & DPP)
        val unit5Subtopics = SubtopicRepository.getSubtopicsForCategory(
            DefaultQuestions.UNIT_5,
            allQuestions,
            emptyList()
        )
        assertEquals("Unit 5 must have 5 subtopics", 5, unit5Subtopics.size)

        // Verify Unit 6 has 18 practice sets with 175 questions total
        val unit6Subtopics = SubtopicRepository.getSubtopicsForCategory(
            DefaultQuestions.UNIT_6,
            allQuestions,
            emptyList()
        )
        assertEquals("Unit 6 must have 18 practice sets", 18, unit6Subtopics.size)
        val unit6TotalQuestions = unit6Subtopics.sumOf { it.questions.size }
        assertEquals("Unit 6 must have 175 questions across 18 sets", 175, unit6TotalQuestions)
    }

    @Test
    fun `verify UserProgressStore persists attempts and bookmarks across app updates`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val store = UserProgressStore(context)

        val testQuestion = "पुस्तकालय विज्ञान के पांच सूत्र किसने दिए?"

        // Record 1 correct attempt
        store.recordAttempt(testQuestion, chosenOption = 2, isCorrect = true)
        store.setBookmark(testQuestion, isBookmarked = true)

        val progress = store.getProgress(testQuestion)
        assertNotNull(progress)
        assertEquals(1, progress?.timesAttempted)
        assertEquals(1, progress?.timesCorrect)
        assertEquals(2, progress?.lastAttemptOption)
        assertEquals(true, progress?.isBookmarked)

        // Record another attempt (wrong this time)
        store.recordAttempt(testQuestion, chosenOption = 1, isCorrect = false)
        val progressAfter = store.getProgress(testQuestion)
        assertNotNull(progressAfter)
        assertEquals(2, progressAfter?.timesAttempted)
        assertEquals(1, progressAfter?.timesCorrect)
        assertEquals(1, progressAfter?.lastAttemptOption)
        assertEquals(true, progressAfter?.isBookmarked)
    }
}
