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
    fun `verify syllabus units and Unit 1 Subtopic 1 initial questions and notes`() {
        val categories = DefaultQuestions.allCategories
        assertEquals(6, categories.size)
        assertTrue(categories.contains(DefaultQuestions.UNIT_1))
        assertTrue(categories.contains(DefaultQuestions.UNIT_2))
        assertTrue(categories.contains(DefaultQuestions.UNIT_3))
        assertTrue(categories.contains(DefaultQuestions.UNIT_4))
        assertTrue(categories.contains(DefaultQuestions.UNIT_5))
        assertTrue(categories.contains(DefaultQuestions.UNIT_6))

        assertEquals("पुस्तकालय की बेसिक अवधारणा", DefaultQuestions.UNIT_1_SUBTOPIC_1)
        assertEquals("https://www.youtube.com/live/XDdMEc3Kvh4?si=tUI3sqQ7en3pCUjn", DefaultQuestions.UNIT_1_SUBTOPIC_1_YOUTUBE_URL)

        assertEquals("पुस्तकालय के प्रकार (Types of Library)", DefaultQuestions.UNIT_1_SUBTOPIC_2)
        assertEquals("https://www.youtube.com/live/wYSPC7-MJIo?si=5T6N6yc7NPTXYnnr", DefaultQuestions.UNIT_1_SUBTOPIC_2_YOUTUBE_URL)

        assertEquals("सार्वजनिक लाइब्रेरी (Public Library)", DefaultQuestions.UNIT_1_SUBTOPIC_3)
        assertEquals("https://www.youtube.com/live/uCEcYZpYJzU?si=h833VB2zim-UX-3S", DefaultQuestions.UNIT_1_SUBTOPIC_3_YOUTUBE_URL)

        assertEquals("भारत के राष्ट्रीय लाइब्रेरी (National Library of India)", DefaultQuestions.UNIT_1_SUBTOPIC_4)
        assertEquals("https://www.youtube.com/live/YdvxrwbvaAs?si=xSxRqQty50dXkmCk", DefaultQuestions.UNIT_1_SUBTOPIC_4_YOUTUBE_URL)

        assertEquals("विशिष्ट लाइब्रेरी (Special Library)", DefaultQuestions.UNIT_1_SUBTOPIC_5)
        assertEquals("https://www.youtube.com/live/wD8Bue1vdGQ?si=WU4c12D48xx0vc-8", DefaultQuestions.UNIT_1_SUBTOPIC_5_YOUTUBE_URL)

        val questions = DefaultQuestions.getInitialQuestions()
        assertEquals("Initial questions include Unit 1 (55) + Unit 6 (80)", 135, questions.size)
        assertEquals(DefaultQuestions.UNIT_1, questions[0].category)

        // Verify Unit 1 subtopics count is exactly 5 (user provided 5 lecture links)
        val unit1Materials = listOf(
            DefaultQuestions.getUnit1Subtopic1Material(),
            DefaultQuestions.getUnit1Subtopic2Material(),
            DefaultQuestions.getUnit1Subtopic3Material(),
            DefaultQuestions.getUnit1Subtopic4Material(),
            DefaultQuestions.getUnit1Subtopic5Material()
        )
        val unit1Subtopics = com.example.data.SubtopicRepository.getSubtopicsForCategory(
            DefaultQuestions.UNIT_1,
            questions,
            unit1Materials
        )
        assertEquals("Unit 1 must have exactly 5 lectures", 5, unit1Subtopics.size)

        // Verify Unit 6 has 8 practice sets with 10 questions each (80 questions total)
        val unit6Subtopics = com.example.data.SubtopicRepository.getSubtopicsForCategory(
            DefaultQuestions.UNIT_6,
            questions,
            emptyList()
        )
        assertEquals("Unit 6 must have 8 practice sets", 8, unit6Subtopics.size)
        val unit6TotalQuestions = unit6Subtopics.sumOf { it.questions.size }
        assertEquals("Unit 6 must have 80 questions across 8 sets", 80, unit6TotalQuestions)

        val videoId1 = com.example.util.ContentSeparator.extractYouTubeVideoId(DefaultQuestions.UNIT_1_SUBTOPIC_1_YOUTUBE_URL)
        assertEquals("XDdMEc3Kvh4", videoId1)

        val videoId2 = com.example.util.ContentSeparator.extractYouTubeVideoId(DefaultQuestions.UNIT_1_SUBTOPIC_2_YOUTUBE_URL)
        assertEquals("wYSPC7-MJIo", videoId2)

        val videoId3 = com.example.util.ContentSeparator.extractYouTubeVideoId(DefaultQuestions.UNIT_1_SUBTOPIC_3_YOUTUBE_URL)
        assertEquals("uCEcYZpYJzU", videoId3)

        val videoId4 = com.example.util.ContentSeparator.extractYouTubeVideoId(DefaultQuestions.UNIT_1_SUBTOPIC_4_YOUTUBE_URL)
        assertEquals("YdvxrwbvaAs", videoId4)

        val videoId5 = com.example.util.ContentSeparator.extractYouTubeVideoId(DefaultQuestions.UNIT_1_SUBTOPIC_5_YOUTUBE_URL)
        assertEquals("wD8Bue1vdGQ", videoId5)

        val separationResult1 = com.example.util.ContentSeparator.separateNotesAndQuestions(
            com.example.util.ContentSeparator.getUnit1Subtopic1FullContent()
        )
        assertTrue("Notes should contain etymology information", separationResult1.notesText.contains("Liber"))
        assertEquals("Should parse 10 questions from Unit 1 Subtopic 1 content", 10, separationResult1.questions.size)

        val separationResult2 = com.example.util.ContentSeparator.separateNotesAndQuestions(
            com.example.util.ContentSeparator.getUnit1Subtopic2FullContent()
        )
        assertTrue("Notes should contain academic library information", separationResult2.notesText.contains("शैक्षणिक पुस्तकालय"))
        assertEquals("Should parse 10 questions from Unit 1 Subtopic 2 content", 10, separationResult2.questions.size)

        val separationResult3 = com.example.util.ContentSeparator.separateNotesAndQuestions(
            com.example.util.ContentSeparator.getUnit1Subtopic3FullContent()
        )
        assertTrue("Notes should contain public library information", separationResult3.notesText.contains("सार्वजनिक पुस्तकालय"))
        assertEquals("Should parse 10 questions from Unit 1 Subtopic 3 content", 10, separationResult3.questions.size)

        val separationResult4 = com.example.util.ContentSeparator.separateNotesAndQuestions(
            com.example.util.ContentSeparator.getUnit1Subtopic4FullContent()
        )
        assertTrue("Notes should contain national library information", separationResult4.notesText.contains("राष्ट्रीय पुस्तकालय"))
        assertEquals("Should parse 15 questions from Unit 1 Subtopic 4 content", 15, separationResult4.questions.size)

        val separationResult5 = com.example.util.ContentSeparator.separateNotesAndQuestions(
            com.example.util.ContentSeparator.getUnit1Subtopic5FullContent()
        )
        assertTrue("Notes should contain special library information", separationResult5.notesText.contains("विशिष्ट पुस्तकालय"))
        assertEquals("Should parse 10 questions from Unit 1 Subtopic 5 content", 10, separationResult5.questions.size)
    }
}
