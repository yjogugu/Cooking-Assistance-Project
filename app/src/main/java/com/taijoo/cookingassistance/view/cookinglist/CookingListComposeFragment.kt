package com.taijoo.cookingassistance.view.cookinglist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.tooling.preview.Preview
import androidx.fragment.app.Fragment
import com.taijoo.cookingassistance.view.ui.ui.theme.CookingAssistanceTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CookingListComposeFragment : Fragment() {

    companion object {
        fun newInstance() = CookingListComposeFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {

                CookingAssistanceTheme {
                    Surface(color = MaterialTheme.colors.background) {
                        View()
                    }
                }
            }
        }
    }

}

@Composable
private fun View(){
    Text(text = "dasdassad")

}