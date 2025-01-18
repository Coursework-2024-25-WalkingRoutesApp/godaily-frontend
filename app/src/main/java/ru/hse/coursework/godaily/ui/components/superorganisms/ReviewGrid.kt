package ru.hse.coursework.godaily.ui.components.superorganisms

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.hse.coursework.godaily.core.data.model.ReviewDTO
import ru.hse.coursework.godaily.ui.components.organisms.ReviewCard
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun ReviewGrid(
    reviews: List<ReviewDTO>,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(scrollState)
    ) {
        reviews.forEach { review ->
            ReviewCard(
                userName = review.username,
                photoUrl = review.photoURL,
                date = formatDate(review.createdAt),
                mark = review.mark,
                reviewText = review.reviewText
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

fun formatDate(date: LocalDateTime): String {
    val formatter = DateTimeFormatter.ofPattern("dd.MM.yy")
    return date.format(formatter)
}


@Preview
@Composable
fun ReviewGridPreview() {
    val sampleReviews = listOf(
        ReviewDTO(
            userId = "0",
            username = "Иван Иванов",
            photoURL = "https://example.com/photo1.jpg",
            mark = 5,
            reviewText = "Этот маршрут превзошел все мои ожидания! Я давно искал что-то подобное — живописные виды, разнообразные ландшафты и интересные достопримечательности. Он идеально подходит для тех, кто хочет увидеть как можно больше за короткое время. Очень понравилось, что на пути были предусмотрены места для отдыха с видом на красивые пейзажи. Обязательно буду рекомендовать своим друзьям, а сам вернусь еще не раз!",
            createdAt = LocalDateTime.of(2023, 12, 25, 14, 30, 0, 0)
        ),
        ReviewDTO(
            userId = "2",
            username = "Мария Петрова",
            photoURL = "https://example.com/photo2.jpg",
            mark = 4,
            reviewText = "Маршрут хороший, но не без недостатков. В целом, он довольно интересный, с красивыми видами и множеством остановок, где можно сделать фото. Однако были некоторые участки, где я чувствовала себя не совсем комфортно: узкие тропинки и недостаточно указателей на некоторых поворотах. Я думаю, что это можно улучшить. В целом, это было приятное путешествие, и я бы снова попробовала пройти этот маршрут.",
            createdAt = LocalDateTime.of(2024, 1, 10, 9, 15, 0, 0)
        ),
        ReviewDTO(
            userId = "3",
            username = "Алексей Браун",
            photoURL = "https://example.com/photo3.jpg",
            mark = 3,
            reviewText = "Маршрут в целом нормальный, но мне показалось, что он слишком простой и не особо захватывающий. Местами не хватает интересных моментов. Для новичков, наверное, будет хорошо, но для более опытных туристов я бы посоветовал выбрать что-то более сложное и увлекательное. В целом, прогулка прошла без особых проблем, но я ожидал большего.",
            createdAt = LocalDateTime.of(2024, 1, 12, 18, 45, 0, 0)
        ),
        ReviewDTO(
            userId = "1",
            username = "Котеночек",
            photoURL = "https://amicus-vet.ru/images/statii/a582d6cs-960.jpg",
            mark = 5,
            reviewText = "Ничего не знаю, у меня лапки",
            createdAt = LocalDateTime.of(2024, 1, 12, 18, 45, 0, 0)
        )
    )

    ReviewGrid(reviews = sampleReviews)
}
