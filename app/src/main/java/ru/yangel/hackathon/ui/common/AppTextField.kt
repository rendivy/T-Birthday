package ru.yangel.hackathon.ui.common

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import ru.yangel.hackathon.ui.theme.CodGray
import ru.yangel.hackathon.ui.theme.Error
import ru.yangel.hackathon.ui.theme.Nevada
import ru.yangel.hackathon.ui.theme.Type10
import ru.yangel.hackathon.ui.theme.Type15

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppOutlinedTextField(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    label: String = "",
    placeholder: String = "",
    value: String,
    onValueChange: (String) -> Unit,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    isError: Boolean = false,
    contentPaddingValues: PaddingValues = PaddingValues(horizontal = 15.dp, vertical = 8.dp),
    colors: TextFieldColors = OutlinedTextFieldDefaults.colors(
        focusedContainerColor = Color.Transparent,
        unfocusedContainerColor = Color.Transparent,
        disabledContainerColor = Color.Transparent,
        unfocusedBorderColor = CodGray,
        focusedBorderColor = CodGray,
        disabledBorderColor = CodGray,
        focusedTextColor = CodGray,
        unfocusedTextColor = CodGray,
        disabledTextColor = CodGray,
        errorContainerColor = Error,
        errorTrailingIconColor = Error,
        errorBorderColor = Error,
        errorCursorColor = Error,
        cursorColor = CodGray
    )
) {
    val interactionSource = remember { MutableInteractionSource() }

    BasicTextField(
        modifier = modifier.fillMaxWidth().height(56.dp),
        value = value,
        onValueChange = onValueChange,
        textStyle = Type15,
        visualTransformation = visualTransformation,
        interactionSource = interactionSource,
        enabled = enabled,
        singleLine = true,
        cursorBrush = if (!isError) SolidColor(CodGray) else SolidColor(Error)
    ) { innerTextField ->
        OutlinedTextFieldDefaults.DecorationBox(value = value,
            colors = colors,
            innerTextField = {
                Column {
                    Text(text = label, style = Type10, color = Nevada)
                    Spacer(modifier = Modifier.height(4.dp))
                    Box {
                        if (value.isEmpty()) {
                            Text(text = placeholder, style = Type15, color = Nevada)
                        }
                        innerTextField()
                    }
                }
            },
            enabled = enabled,
            singleLine = true,
            visualTransformation = visualTransformation,
            interactionSource = interactionSource,
            isError = isError,
            contentPadding = contentPaddingValues,
            container = {
                OutlinedTextFieldDefaults.ContainerBox(
                    enabled = enabled,
                    isError = isError,
                    interactionSource = interactionSource,
                    colors = colors,
                    shape = RoundedCornerShape(7.dp),
                    focusedBorderThickness = 1.dp,
                    unfocusedBorderThickness = 1.dp
                )
            })
    }
}