package com.taijoo.cookingassistance.view.ui

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.taijoo.cookingassistance.view.ui.ui.theme.CookingAssistanceTheme

class StorageMaterialSettingView : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CookingAssistanceTheme {

                HeaderPreView()

            }
        }
    }


}
@Preview(showBackground = true , name = "Light Mode")
@Preview(showBackground = true , name = "Dark Mode" , uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun HeaderPreView(){
//    Header()
}

