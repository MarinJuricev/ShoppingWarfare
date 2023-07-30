package com.marinj.shoppingwarfare.ui.text

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow

@Composable
fun DisplayLarge(
    text: String,
    modifier: Modifier,
    textAlign: TextAlign,
    softWrap: Boolean = true,
    overflow: TextOverflow = TextOverflow.Ellipsis,
    maxLines: Int = Int.MAX_VALUE,
) {
    Text(
        text = text,
        modifier = modifier,
        style = ,
        textAlign = textAlign,
        softWrap = softWrap,
        overflow = overflow,
        maxLines = maxLines
    )
}

@Composable
fun DisplayMedium(
    text: String,
) {
    Text(
        text = text,
    )
}
@Composable
fun DisplaySmall(
    text: String,
) {
    Text(
        text = text,
    )
}
@Composable
fun HeadlineLarge() {

}
@Composable
fun HeadlineMedium() {

}
@Composable
fun HeadlineSmall() {

}

@Composable
fun TitleLarge() {

}
@Composable
fun TitleMedium() {

}
@Composable
fun TitleSmall() {

}

@Composable
fun BodyLarge() {

}
@Composable
fun BodyMedium() {

}
@Composable
fun BodySmall() {

}

@Composable
fun LabelLarge() {

}
@Composable
fun LabelMedium() {

}
@Composable
fun LabelSmall() {

}
