package com.alvindev.traverseeid.feature_campaign.presentation.campaign_details

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alvindev.traverseeid.R
import com.alvindev.traverseeid.core.presentation.component.TraverseeButton
import com.alvindev.traverseeid.core.presentation.component.TraverseeDivider
import com.alvindev.traverseeid.core.presentation.component.TraverseeTextField
import com.alvindev.traverseeid.core.theme.Shapes
import com.alvindev.traverseeid.core.theme.TraverseeTheme
import com.alvindev.traverseeid.core.theme.Typography
import com.alvindev.traverseeid.feature_campaign.domain.constant.CampaignParticipantConstant
import com.alvindev.traverseeid.feature_campaign.presentation.component.CampaignDescriptionCard
import com.alvindev.traverseeid.feature_campaign.presentation.component.CampaignWinnerItem
import com.alvindev.traverseeid.feature_campaign.presentation.component.SectionTitle
import com.alvindev.traverseeid.navigation.ScreenRoute
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator

@Destination(
    route = ScreenRoute.CampaignDetails,
)
@Composable
fun CampaignDetailsScreen(
    id: Int = -1,
    name: String?,
    navigator: DestinationsNavigator
) {
    var campaignUserCondition by rememberSaveable { mutableStateOf(CampaignParticipantConstant.NOT_REGISTERED) }
    var textButton by rememberSaveable { mutableStateOf("Register") }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(bottom = 96.dp),
        ) {
            Box(modifier = Modifier.aspectRatio(1f)){
                Image(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(id = R.drawable.dummy_borobudur),
                    contentDescription = "Borobudur",
                    alignment = Alignment.Center,
                    contentScale = ContentScale.Crop
                )

                if(campaignUserCondition >= CampaignParticipantConstant.REGISTERED_AND_SUBMITTED) {
                    Icon(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(8.dp)
                            .size(24.dp)
                            .background(color = Color.White, shape = RoundedCornerShape(50))
                            .padding(2.dp),
                        imageVector = Icons.Default.Check,
                        contentDescription = "Campaign Ended",
                        tint = Color.Green,
                    )
                }
            }
            Text(
                modifier = Modifier.padding(16.dp),
                text = "Share your story about Kota Lama Semarang",
                style = Typography.h1.copy(color = MaterialTheme.colors.primaryVariant)
            )
            CampaignDescriptionCard(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .shadow(2.dp, shape = Shapes.large)
                    .clip(Shapes.large)
                    .background(color = Color.White)
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                iniatedBy = "Alvin Dev",
                startDate = "12/11/2021",
                endDate = "12/12/2021",
                category = "Category",
                participants = 10
            )
            CampaignWinners(
                onClickAllParticipants ={
                    navigator.navigate(ScreenRoute.CampaignParticipants)
                }
            )
            TraverseeDivider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                thickness = 4.dp
            )
            AboutCampaign()
            TraverseeDivider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                thickness = 4.dp
            )
            CampaignTermsAndConditions()
            TraverseeDivider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                thickness = 4.dp
            )
            CampaignMissions()
            if(campaignUserCondition > CampaignParticipantConstant.NOT_REGISTERED) {
                CampaignSubmission()
            }
        }

        TraverseeButton(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 20.dp, start = 16.dp, end = 16.dp),
            text = textButton,
            onClick = {
                when (campaignUserCondition) {
                    CampaignParticipantConstant.NOT_REGISTERED -> {
                        campaignUserCondition = CampaignParticipantConstant.REGISTERED
                        textButton = "Submit"
                    }
                    CampaignParticipantConstant.REGISTERED -> {
                        campaignUserCondition = CampaignParticipantConstant.REGISTERED_AND_SUBMITTED
                        textButton = "Already Submitted"
                    }
                    CampaignParticipantConstant.REGISTERED_AND_SUBMITTED -> {
                        campaignUserCondition = CampaignParticipantConstant.ENDED
                        textButton = "Ended"
                    }
                }
            },
            enabled = campaignUserCondition < CampaignParticipantConstant.ENDED
        )
    }
}

@Composable
fun AboutCampaign() {
    Column(
        modifier = Modifier.padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        SectionTitle(title = "About Campaign")
        Text(
            text = "Kota Lama Semarang (Jawa: ꦑꦶꦛ\u200Bꦭꦩ\u200Bꦯꦼꦩꦫꦁ, translit. Kitha Lama Semarang, bahasa Belanda: Semarang Oude Stad) adalah suatu kawasan di Semarang yang menjadi pusat perdagangan pada abad 19–20 . Pada masa itu, untuk mengamankan warga dan wilayahnya, kawasan itu dibangun benteng, yang dinamai benteng Vijfhoek. Untuk mempercepat jalur perhubungan antar ketiga pintu gerbang di benteng itu maka dibuat jalan-jalan perhubungan, dengan jalan utamanya dinamai Heerenstraat. Saat ini bernama Jl. Letjen Soeprapto. Salah satu lokasi pintu benteng yang ada sampai saat ini adalah Jembatan Berok, yang disebut De Zuider Por. Kata 'Berok' sendiri merupakan hasil pelafalan masyarakat Pribumi yang kesulitan melafalkan kata 'Burg' dalam bahasa Belanda.",
            style = Typography.body2,
            textAlign = TextAlign.Justify,
        )
    }
}

@Composable
fun CampaignTermsAndConditions() {
    val termsAndConditions = listOf(
        "Buat video berdurasi maksimal 2 menit menceritakan pengalamanmu ketika mengunjungi kota lama semarang",
        "Buat video berdurasi maksimal 2 menit menceritakan pengalamanmu ketika mengunjungi kota lama semarang",
        "Buat video berdurasi maksimal 2 menit menceritakan pengalamanmu ketika mengunjungi kota lama semarang"
    )

    Column(
        modifier = Modifier.padding(horizontal = 16.dp),
    ) {
        SectionTitle(
            modifier = Modifier.padding(bottom = 8.dp),
            title = "Terms and Conditions"
        )
        termsAndConditions.forEachIndexed { index, s ->
            Text(
                modifier = Modifier.padding(bottom = 4.dp),
                text = "${index + 1}. $s",
                style = Typography.body2,
                textAlign = TextAlign.Justify,
            )
        }
    }
}

@Composable
fun CampaignMissions() {
    Column(
        modifier = Modifier.padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        SectionTitle(
            title = "Missions"
        )
        Text(
            text = "Post video ke sosial media favoritmu dengan caption yang menarik dan mencatumkan hastag #CampaignWithTraversee #TraverseeKotaLamaSemarang",
            style = Typography.body2,
            textAlign = TextAlign.Justify,
        )
    }
}

@Composable
fun CampaignSubmission(){
    Column{
        TraverseeDivider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            thickness = 4.dp
        )
        SectionTitle(
            modifier= Modifier.padding(start=16.dp, end=16.dp, bottom = 8.dp),
            title = "Submission"
        )
        TraverseeTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            label = {
                Text("Attach Proof Link")
            },
            placeholder = {
                Text("https://instagram.com/...")
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Done),
        )
    }
}

@Composable
fun CampaignWinners(
    onClickAllParticipants: () -> Unit,
){
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        TraverseeDivider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            thickness = 4.dp
        )
        SectionTitle(
            modifier = Modifier.padding(horizontal = 16.dp),
            title = "Winners Announcement"
        )
        Text(
            modifier = Modifier.padding(start=16.dp, end=16.dp, bottom = 8.dp),
            text = "Congratulations for the winner of this campaign, we’ve emailed you and please confirm your reward before 20 June 2023!",
            style = Typography.body2,
            textAlign = TextAlign.Justify,
        )
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .shadow(2.dp, shape = Shapes.large)
                .fillMaxWidth()
                .background(color = Color.White, shape = Shapes.large)
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            CampaignWinnerItem(
                winnerName = "Alvin",
                winnerPhoto = "",
                winnerRank = 1,
                winnerSubmission = "https://www.google.com"
            )
            CampaignWinnerItem(
                winnerName = "Triseptia",
                winnerPhoto = "",
                winnerRank = 2,
                winnerSubmission = "https://www.google.com"
            )
            CampaignWinnerItem(
                winnerName = "Alphine",
                winnerPhoto = "",
                winnerRank = 3,
                winnerSubmission = "https://www.google.com"
            )
            TextButton(
                onClick = onClickAllParticipants
            ) {
                Text(
                    text = "See All Participants",
                    style = Typography.subtitle2,
                    color = MaterialTheme.colors.primary,
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CampaignDetailsScreenPreview() {
    TraverseeTheme() {
        CampaignDetailsScreen(
            id = 0,
            name = "name",
            navigator = EmptyDestinationsNavigator
        )
    }
}