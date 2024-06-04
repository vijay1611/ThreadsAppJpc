package com.vijay.threadsappjpc.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.rememberAsyncImagePainter
import com.vijay.threadsappjpc.R
import com.vijay.threadsappjpc.utils.SharedPref

@Composable
fun AddThreads(){

    val context = LocalContext.current

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        val (crossPic ,text, logo,userName,editText,attachMedia,replyText,button,imageBox) = createRefs()

        Image(painter = painterResource(id = R.drawable.baseline_switch_access_shortcut_24), contentDescription = "close",
            modifier = Modifier
                .constrainAs(crossPic) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                }
                .clickable {

                })
        Text(text = "Add Thread", style = TextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp),
            modifier = Modifier.constrainAs(text){
                top.linkTo(crossPic.top)
                start.linkTo(crossPic.end, margin = 12.dp)
                bottom.linkTo(crossPic.bottom)

            }
        )
        Image(painter = rememberAsyncImagePainter(model = SharedPref.getimageUrl(context)), contentDescription = "close",
            modifier = Modifier
                .constrainAs(logo) {
                    top.linkTo(text.bottom)
                    start.linkTo(parent.start)
                })
        Text(text = SharedPref.getUserName(context),
            style = TextStyle(fontSize = 20.sp
                ), modifier = Modifier.constrainAs(userName){
                    top.linkTo(logo.top)
                start.linkTo(logo.end, margin = 12.dp)
                bottom.linkTo(logo.bottom)
            }
            )


    
    }

}