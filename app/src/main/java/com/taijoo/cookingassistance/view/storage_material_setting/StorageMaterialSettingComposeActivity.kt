package com.taijoo.cookingassistance.view.storage_material_setting

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.focusable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.taijoo.cookingassistance.R
import com.taijoo.cookingassistance.util.CustomDateDialog
import com.taijoo.cookingassistance.util.CustomDefaultDialog
import com.taijoo.cookingassistance.view.ui.Header
import com.taijoo.cookingassistance.view.ui.ui.theme.CookingAssistanceTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class StorageMaterialSettingComposeActivity : ComponentActivity() {

    private val viewModel : StorageMaterialSettingViewModel by viewModels()


    @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.setSeq(intent.getLongExtra("seq",0))

        setContent {
            CookingAssistanceTheme {

                Surface(color = MaterialTheme.colors.background) {
                    View()

                }

            }
        }

    }


}

@Composable
private fun View(viewModel: StorageMaterialSettingViewModel = viewModel()){

    val activity = (LocalContext.current as Activity)


    Column {
        Header(onSetDeleteDialog = {setDeleteDialog(activity = activity ,onSetDelete = {
            viewModel.setDelete()
        })})
        Body(activity,viewModel)
    }

}

//재료 삭제 다이얼로그
private fun setDeleteDialog(activity: Activity , onSetDelete : () -> Unit){
    val customDefaultDialog = CustomDefaultDialog(activity,"재료삭제","재료를 삭제 하시겠습니까?")

    customDefaultDialog.setDialogListener(object : CustomDefaultDialog.CustomDialogListener{
        override fun onCheckClick() {
            onSetDelete()
            activity.finish()
        }

        override fun onNoClick() {

        }

    })
    customDefaultDialog.show()
}

//기간 설정 다이얼로그
private fun setDate(activity: Activity , viewModel: StorageMaterialSettingViewModel , type : Int){

    val customDateDialog = if(type == 0){//구매날짜
        CustomDateDialog(activity , activity.getString(R.string.date_dialog2), viewModel.storageData.value.date)
    }
    else{//유통기한
        CustomDateDialog(activity ,activity.getString(R.string.date_dialog1), viewModel.storageData.value.expiration_date)
    }

    customDateDialog.setOnClickListener(object : CustomDateDialog.CustomCategoryDialogListener{
        override fun onOkClick(date : String) {
            if(type == 0){
                viewModel.setDate(date)
            }
            else{
                viewModel.setExpirationDate(date)
            }

        }

        override fun onNoClick() {

        }

    })

    customDateDialog.show()
}


//백그라운드 포커스 잡기
private fun Modifier.addFocusCleaner(focusManager: FocusManager, doOnClear: () -> Unit = {}): Modifier {

    return this.pointerInput(Unit) {
        detectTapGestures(onTap = {
            doOnClear()
            focusManager.clearFocus()
        })
    }

}

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun Body(activity: Activity , viewModel : StorageMaterialSettingViewModel ){

    val focusManager : FocusManager = LocalFocusManager.current//배경 포커스 잡기

    Column(modifier = Modifier
        .fillMaxHeight()
        .addFocusCleaner(
            focusManager
        )) {

        Name(name = viewModel.storageData.collectAsState().value.name)

        Size(size = viewModel.storageData.collectAsState().value.size.toString() , focusManager = focusManager , onSize = {
            viewModel.setSize(it)
        })

        Date(activity = activity , viewModel = viewModel , date = viewModel.storageData.collectAsState().value.date , type = 0 , focusManager = focusManager)
        Date(activity = activity , viewModel = viewModel , date = viewModel.storageData.collectAsState().value.expiration_date , type = 1 , focusManager = focusManager)


        Note(note = viewModel.storageData.collectAsState().value.note , onNote = {
            viewModel.setNote(it)
        })

        Spacer(modifier = Modifier.weight(1f))

        OkButton(onFinish = { activity.finish() } , onSetData = { viewModel.setData()})
    }

}

//재료 이름
@Composable
fun Name(name : String){
    Column(modifier = Modifier.fillMaxWidth() , horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = name  ,fontSize = 20.sp ,  color = colorResource(id = R.color.color_000000))
    }
}

//재료 갯수
@Composable
fun Size(size : String , focusManager : FocusManager , onSize : (String) -> Unit ){
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(20.dp, 50.dp, 20.dp, 10.dp)) {

        Row {
            Text(text = "갯수 : " , color = colorResource(id = R.color.color_000000) ,fontSize = 17.sp  , modifier = Modifier
                .align(
                    Alignment.CenterVertically
                )
                .padding(10.dp))

            BasicTextField(
                value = size,
                onValueChange = {
                    onSize(it)
//                    viewModel.setSize(it)
                }  ,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterVertically),
                textStyle = TextStyle(fontSize = 17.sp ,color = colorResource(id = R.color.color_000000)) ,
                maxLines = 1 ,
                keyboardOptions = KeyboardOptions(keyboardType= KeyboardType.Number , imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = {
                    focusManager.clearFocus()
                }))


        }
    }
}

//구입한 날짜 or 유통기한
@Composable
fun Date(activity: Activity, date : String , viewModel: StorageMaterialSettingViewModel , type: Int , focusManager: FocusManager ){

    val title = if(type == 0){
        "구매날짜 : "
    }
    else{
        "유통기한 : "
    }
    //type 0:구입한날짜 , 1:유통기한
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(20.dp, 20.dp, 20.dp, 10.dp)) {

        Row {
            Button(onClick = {
                setDate(activity,viewModel,type)
                focusManager.clearFocus()

            },
                colors =  ButtonDefaults.buttonColors(Color.Transparent) , elevation = ButtonDefaults.elevation(
                    defaultElevation = 0.dp ,
                    pressedElevation = 0.dp ,
                    disabledElevation = 0.dp
                ) , contentPadding = PaddingValues(0.dp)){

                Text(text = title , color = colorResource(id = R.color.color_000000) ,fontSize = 17.sp  , modifier = Modifier
                    .align(
                        Alignment.CenterVertically
                    )
                    .padding(10.dp))

                Text(text = date , color = colorResource(id = R.color.color_000000) ,fontSize = 17.sp  , modifier = Modifier
                    .align(
                        Alignment.CenterVertically
                    )
                    .weight(1f))

                Image(painter = painterResource(id = R.drawable.ic_edit_24), contentDescription = "edit" , colorFilter = ColorFilter.tint(
                    colorResource(id = R.color.color_000000)) , modifier = Modifier.align(Alignment.CenterVertically))

            }

        }
    }
}

//메모
@Composable
fun Note(note : String , onNote : (String) -> Unit){
    Column(modifier = Modifier
        .padding(20.dp, 30.dp, 20.dp, 0.dp)) {

        TextField(value = note,
            onValueChange = {
                onNote(it)
            },
            modifier = Modifier
                .fillMaxWidth()
                .defaultMinSize(minHeight = 300.dp)
                .border(
                    width = 1.dp, shape = RoundedCornerShape(20.dp), color = colorResource(
                        id = R.color.color_DDDDDD
                    )
                )
            , colors = TextFieldDefaults.textFieldColors(focusedIndicatorColor = Color.Transparent  , backgroundColor = Color.Transparent
                , textColor = colorResource(id = R.color.color_000000) , unfocusedIndicatorColor = Color.Transparent) ,

            textStyle = TextStyle(fontSize = 15.sp)
        )

    }
}

//완료버튼
@Composable
fun OkButton(onFinish : ()->Unit , onSetData : ()->Unit){
    Button(onClick = {
        onFinish()
        onSetData()
    } ,
        modifier = Modifier.fillMaxWidth() , colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(
            id = R.color.color_000000)) , elevation = ButtonDefaults.elevation(
            defaultElevation = 0.dp ,
            pressedElevation = 0.dp ,
            disabledElevation = 0.dp
        )) {
        Text(text = "저장" , color = colorResource(id = R.color.color_FFFFFF))

    }
}