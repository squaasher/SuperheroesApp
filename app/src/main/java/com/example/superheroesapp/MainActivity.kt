package com.example.superheroesapp

import android.os.Bundle
import android.support.v4.os.IResultReceiver.Default
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.InspectableModifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.superheroesapp.data.HeroesRepository.heroes
import com.example.superheroesapp.data.HeroesRepository.indHeroes
import com.example.superheroesapp.model.Hero
import com.example.superheroesapp.ui.theme.SuperheroesAppTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SuperheroesAppTheme {
                SuperheroApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SuperheroApp() {
    Scaffold(
        topBar = {SuperheroTopBar()}
    ){ padding ->
        LazyColumn(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(padding)
        ){
            items(heroes){
                SuperheroItem(hero = it)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SuperheroItem(hero: Hero, modifier: Modifier = Modifier){
    var expanded by remember { mutableStateOf(false) }
    var checkOutList: MutableList<Hero> = ArrayList(indHeroes)
    checkOutList.remove(hero)

    Card (
        onClick = {expanded = !expanded},
        elevation = CardDefaults.cardElevation(2.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        modifier = modifier
            .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp)
    ){
        Column(
            modifier = Modifier
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessMediumLow
                     )
                )
        ){
            SuperheroInformation(hero.nameRes, hero.descriptionRes, hero.imageRes)
            if(expanded){
                Text(
                    text = "Wow! ${stringResource(id = hero.nameRes)} is really cool! " +
                            "And if you think ${stringResource(id = hero.nameRes)} is cool, " +
                            "you should really check out ${stringResource(id = checkOutList.random().nameRes)} if you haven't already. You might be surprised!",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier
                        .padding(16.dp)
                )
            }
        }

    }
}

@Composable
fun SuperheroInformation(
    @StringRes titleTextRes: Int,
    @StringRes descriptionTextRes: Int,
    @DrawableRes iconRes: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)

    ){
        Column(
            modifier = Modifier.weight(1f, true)
        ){
            Text(
                text = stringResource(id = titleTextRes),
                style = MaterialTheme.typography.displaySmall
            )
            Text(
                text = stringResource(id = descriptionTextRes),
                style = MaterialTheme.typography.bodyLarge,
            )
        }
        Spacer(modifier = modifier.width(16.dp))
        Image(
            painter = painterResource(id = iconRes),
            contentDescription = null,
            modifier = modifier
                .clip(RoundedCornerShape(8.dp))
                .size(72.dp)
        )
    }
}

@Composable
fun SuperheroTopBar(modifier: Modifier = Modifier) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
    ){
        Text(
            text = "Superheroes",
            style = MaterialTheme.typography.displayLarge
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SuperheroAppPreview() {
    SuperheroesAppTheme(darkTheme = false) {
        SuperheroApp()
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SuperheroAppDarkPreview() {
    SuperheroesAppTheme(darkTheme = true) {
        SuperheroApp()
    }
}

