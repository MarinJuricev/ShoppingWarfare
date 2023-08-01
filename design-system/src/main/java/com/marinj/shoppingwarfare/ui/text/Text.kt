package com.marinj.shoppingwarfare.ui.text

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun TextDisplayLarge(
    text: String,
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Start,
    color: Color = Color.Black,
    softWrap: Boolean = true,
    overflow: TextOverflow = TextOverflow.Ellipsis,
    maxLines: Int = Int.MAX_VALUE,
) {
    Text(
        text = text,
        modifier = modifier,
        style = SWTypography.DisplayLarge,
        textAlign = textAlign,
        color = color,
        softWrap = softWrap,
        overflow = overflow,
        maxLines = maxLines,
    )
}

@Composable
fun TextDisplayMedium(
    text: String,
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Start,
    color: Color = Color.Black,
    softWrap: Boolean = true,
    overflow: TextOverflow = TextOverflow.Ellipsis,
    maxLines: Int = Int.MAX_VALUE,
) {
    Text(
        text = text,
        modifier = modifier,
        style = SWTypography.DisplayMedium,
        textAlign = textAlign,
        color = color,
        softWrap = softWrap,
        overflow = overflow,
        maxLines = maxLines,
    )
}

@Composable
fun TextDisplaySmall(
    text: String,
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Start,
    color: Color = Color.Black,
    softWrap: Boolean = true,
    overflow: TextOverflow = TextOverflow.Ellipsis,
    maxLines: Int = Int.MAX_VALUE,
) {
    Text(
        text = text,
        modifier = modifier,
        style = SWTypography.DisplaySmall,
        textAlign = textAlign,
        color = color,
        softWrap = softWrap,
        overflow = overflow,
        maxLines = maxLines,
    )
}

@Composable
fun TextHeadlineLarge(
    text: String,
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Start,
    color: Color = Color.Black,
    softWrap: Boolean = true,
    overflow: TextOverflow = TextOverflow.Ellipsis,
    maxLines: Int = Int.MAX_VALUE,
) {
    Text(
        text = text,
        modifier = modifier,
        style = SWTypography.DisplayLarge,
        textAlign = textAlign,
        color = color,
        softWrap = softWrap,
        overflow = overflow,
        maxLines = maxLines,
    )
}

@Composable
fun TextHeadlineMedium(
    text: String,
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Start,
    color: Color = Color.Black,
    softWrap: Boolean = true,
    overflow: TextOverflow = TextOverflow.Ellipsis,
    maxLines: Int = Int.MAX_VALUE,
) {
    Text(
        text = text,
        modifier = modifier,
        style = SWTypography.HeadlineMedium,
        textAlign = textAlign,
        color = color,
        softWrap = softWrap,
        overflow = overflow,
        maxLines = maxLines,
    )
}

@Composable
fun TextHeadlineSmall(
    text: String,
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Start,
    color: Color = Color.Black,
    softWrap: Boolean = true,
    overflow: TextOverflow = TextOverflow.Ellipsis,
    maxLines: Int = Int.MAX_VALUE,
) {
    Text(
        text = text,
        modifier = modifier,
        style = SWTypography.HeadlineSmall,
        textAlign = textAlign,
        color = color,
        softWrap = softWrap,
        overflow = overflow,
        maxLines = maxLines,
    )
}

@Composable
fun TextTitleLarge(
    text: String,
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Start,
    color: Color = Color.Black,
    softWrap: Boolean = true,
    overflow: TextOverflow = TextOverflow.Ellipsis,
    maxLines: Int = Int.MAX_VALUE,
) {
    Text(
        text = text,
        modifier = modifier,
        style = SWTypography.TitleLarge,
        textAlign = textAlign,
        color = color,
        softWrap = softWrap,
        overflow = overflow,
        maxLines = maxLines,
    )
}

@Composable
fun TextTitleMedium(
    text: String,
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Start,
    color: Color = Color.Black,
    softWrap: Boolean = true,
    overflow: TextOverflow = TextOverflow.Ellipsis,
    maxLines: Int = Int.MAX_VALUE,
) {
    Text(
        text = text,
        modifier = modifier,
        style = SWTypography.TitleMedium,
        textAlign = textAlign,
        color = color,
        softWrap = softWrap,
        overflow = overflow,
        maxLines = maxLines,
    )
}

@Composable
fun TextTitleSmall(
    text: String,
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Start,
    color: Color = Color.Black,
    softWrap: Boolean = true,
    overflow: TextOverflow = TextOverflow.Ellipsis,
    maxLines: Int = Int.MAX_VALUE,
) {
    Text(
        text = text,
        modifier = modifier,
        style = SWTypography.TitleSmall,
        textAlign = textAlign,
        color = color,
        softWrap = softWrap,
        overflow = overflow,
        maxLines = maxLines,
    )
}

@Composable
fun TextBodyLarge(
    text: String,
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Start,
    color: Color = Color.Black,
    softWrap: Boolean = true,
    overflow: TextOverflow = TextOverflow.Ellipsis,
    maxLines: Int = Int.MAX_VALUE,
) {
    Text(
        text = text,
        modifier = modifier,
        style = SWTypography.BodyLarge,
        textAlign = textAlign,
        color = color,
        softWrap = softWrap,
        overflow = overflow,
        maxLines = maxLines,
    )
}

@Composable
fun TextBodyMedium(
    text: String,
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Start,
    color: Color = Color.Black,
    softWrap: Boolean = true,
    overflow: TextOverflow = TextOverflow.Ellipsis,
    maxLines: Int = Int.MAX_VALUE,
) {
    Text(
        text = text,
        modifier = modifier,
        style = SWTypography.BodyMedium,
        textAlign = textAlign,
        color = color,
        softWrap = softWrap,
        overflow = overflow,
        maxLines = maxLines,
    )
}

@Composable
fun TextBodySmall(
    text: String,
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Start,
    color: Color = Color.Black,
    softWrap: Boolean = true,
    overflow: TextOverflow = TextOverflow.Ellipsis,
    maxLines: Int = Int.MAX_VALUE,
) {
    Text(
        text = text,
        modifier = modifier,
        style = SWTypography.BodySmall,
        textAlign = textAlign,
        color = color,
        softWrap = softWrap,
        overflow = overflow,
        maxLines = maxLines,
    )
}

@Composable
fun TextLabelLarge(
    text: String,
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Start,
    color: Color = Color.Black,
    softWrap: Boolean = true,
    overflow: TextOverflow = TextOverflow.Ellipsis,
    maxLines: Int = Int.MAX_VALUE,
) {
    Text(
        text = text,
        modifier = modifier,
        style = SWTypography.LabelLarge,
        textAlign = textAlign,
        color = color,
        softWrap = softWrap,
        overflow = overflow,
        maxLines = maxLines,
    )
}

@Composable
fun TextLabelMedium(
    text: String,
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Start,
    color: Color = Color.Black,
    softWrap: Boolean = true,
    overflow: TextOverflow = TextOverflow.Ellipsis,
    maxLines: Int = Int.MAX_VALUE,
) {
    Text(
        text = text,
        modifier = modifier,
        style = SWTypography.LabelMedium,
        textAlign = textAlign,
        color = color,
        softWrap = softWrap,
        overflow = overflow,
        maxLines = maxLines,
    )
}

@Composable
fun TextLabelSmall(
    text: String,
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Start,
    color: Color = Color.Black,
    softWrap: Boolean = true,
    overflow: TextOverflow = TextOverflow.Ellipsis,
    maxLines: Int = Int.MAX_VALUE,
) {
    Text(
        text = text,
        modifier = modifier,
        style = SWTypography.LabelSmall,
        textAlign = textAlign,
        color = color,
        softWrap = softWrap,
        overflow = overflow,
        maxLines = maxLines,
    )
}

@Composable
fun SpannedText(
    fullText: String,
    spannedTexts: List<String>,
    modifier: Modifier = Modifier,
    spannedStyle: TextStyle = SWTypography.BodyMedium,
    textStyle: TextStyle = SWTypography.BodyMedium,
    spannedTextColor: Color = Color.Red,
    textColor: Color = Color.Black,
) {
    val spanStyle = remember(spannedTextColor, spannedStyle) {
        SpanStyle(
            color = spannedTextColor,
            fontSize = spannedStyle.fontSize,
            fontWeight = spannedStyle.fontWeight,
            fontFamily = spannedStyle.fontFamily,
        )
    }

    val spannedStyles: List<AnnotatedString.Range<SpanStyle>> = remember(spannedTexts) {
        buildList {
            for (spanned in spannedTexts) {
                val startIndex = fullText.indexOf(spanned)
                if (startIndex == -1) continue
                add(
                    AnnotatedString.Range(
                        item = spanStyle,
                        start = startIndex,
                        end = startIndex + spanned.length,
                    ),
                )
            }
        }
    }

    Text(
        modifier = modifier,
        text = AnnotatedString(
            text = fullText,
            spanStyles = spannedStyles,
        ),
        style = textStyle,
        color = textColor,
    )
}

@Composable
@Preview
fun TextDisplayLargePreview() {
    TextDisplayLarge(text = "TextDisplayLarge")
}
@Composable
@Preview
fun TextDisplayMediumPreview() {
    TextDisplayMedium(text = "TextDisplayMedium")
}

@Composable
@Preview
fun TextDisplaySmallPreview() {
    TextDisplaySmall(text = "TextDisplaySmall")
}

@Composable
@Preview
fun TextHeadlineLargePreview() {
    TextHeadlineLarge(text = "TextHeadlineLarge")
}

@Composable
@Preview
fun TextHeadlineMediumPreview() {
    TextHeadlineMedium(text = "TextHeadlineMedium")
}

@Composable
@Preview
fun TextHeadlineSmallPreview() {
    TextHeadlineSmall(text = "TextHeadlineSmall")
}

@Composable
@Preview
fun TextTitleLargePreview() {
    TextTitleLarge(text = "TextTitleLarge")
}

@Composable
@Preview
fun TextTitleMediumPreview() {
    TextTitleMedium(text = "TextTitleMedium")
}

@Composable
@Preview
fun TextTitleSmallPreview() {
    TextTitleSmall(text = "TextTitleSmall")
}

@Composable
@Preview
fun TextBodyLargePreview() {
    TextBodyLarge(text = "TextBodyLarge")
}

@Composable
@Preview
fun TextBodyMediumPreview() {
    TextBodyMedium(text = "TextBodyMedium")
}

@Composable
@Preview
fun TextBodySmallPreview() {
    TextBodySmall(text = "TextBodySmall")
}

@Composable
@Preview
fun TextLabelLargePreview() {
    TextLabelLarge(text = "TextLabelLarge")
}

@Composable
@Preview
fun TextLabelMediumPreview() {
    TextLabelMedium(text = "TextLabelMedium")
}

@Composable
@Preview
fun TextLabelSmallPreview() {
    TextLabelSmall(text = "TextLabelSmall")
}

@Composable
@Preview
fun SpannedTextPreview() {
    SpannedText(
        fullText = "SpannedText",
        spannedTexts = listOf("Spanned"),
    )
}
