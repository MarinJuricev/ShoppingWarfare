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
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider

@Composable
@Preview
fun DisplayLarge(
    @PreviewParameter(TextConfigProvider::class) text: String,
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
fun DisplayMedium(
    @PreviewParameter(TextConfigProvider::class) text: String,
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
@Preview
fun DisplaySmall(
    @PreviewParameter(TextConfigProvider::class) text: String,
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
@Preview
fun HeadlineLarge(
    @PreviewParameter(TextConfigProvider::class) text: String,
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
@Preview
fun HeadlineMedium(
    @PreviewParameter(TextConfigProvider::class) text: String,
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
@Preview
fun HeadlineSmall(
    @PreviewParameter(TextConfigProvider::class) text: String,
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
@Preview
fun TitleLarge(
    @PreviewParameter(TextConfigProvider::class) text: String,
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
@Preview
fun TitleMedium(
    @PreviewParameter(TextConfigProvider::class) text: String,
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
@Preview
fun TitleSmall(
    @PreviewParameter(TextConfigProvider::class) text: String,
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
@Preview
fun BodyLarge(
    @PreviewParameter(TextConfigProvider::class) text: String,
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
@Preview
fun BodyMedium(
    @PreviewParameter(TextConfigProvider::class) text: String,
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
@Preview
fun BodySmall(
    @PreviewParameter(TextConfigProvider::class) text: String,
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
@Preview
fun LabelLarge(
    @PreviewParameter(TextConfigProvider::class) text: String,
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
@Preview
fun LabelMedium(
    @PreviewParameter(TextConfigProvider::class) text: String,
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
@Preview
fun LabelSmall(
    @PreviewParameter(TextConfigProvider::class) text: String,
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

// TODO: Provide a preview for this
@Composable
fun SpannedText(
    fullText: String,
    spannedTexts: List<String>,
    modifier: Modifier = Modifier,
    spannedStyle: TextStyle = SWTypography.BodyMedium,
    textStyle: TextStyle = SWTypography.BodyMedium,
    spannedTextColor: Color = Color.Black,
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

private class TextConfigProvider : PreviewParameterProvider<String> {
    override val values: Sequence<String> = sequenceOf(
        "Primary text", "Secondary text", "Tertiary text", "Disabled text",
    )
}
