import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import org.junit.Rule
import org.junit.Test

class TestComponentes {
    @get:Rule
    val composeTestRule = createComposeRule()


    @Test
    fun testButton(){
        val text = mutableStateOf("test")
        composeTestRule.setContent {
            AddTextField(text = text.value, placeHolder =  " ", onValueChange = {text.value = it})
            AddButton("") {text.value = "a"}
        }

        composeTestRule.onNodeWithTag("Button").performClick()
        composeTestRule.onNodeWithTag("TextField").assertTextEquals("a")
    }

    @Test
    fun testPlainText(){
        val text = mutableStateOf("test")
        composeTestRule.setContent {
            AddPlainText(text.value)
        }

        composeTestRule.onNodeWithTag("PlainText").performClick()
        composeTestRule.onNodeWithTag("PlainText").assertIsNotEnabled()
    }

    @Test
    fun
}