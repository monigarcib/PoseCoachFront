package com.example.actualizar.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.actualizar.ui.screens.profile.ChangeEmailScreen
import com.example.actualizar.ui.screens.profile.ChangePhoneScreen
import com.example.actualizar.ui.screens.profile.ChangeUsernameScreen
import com.example.actualizar.ui.screens.profile.ProfileScreen
import com.example.actualizar.ui.screens.profile.UpdateAccountScreen
import com.example.actualizar.ui.screens.profile.ChangeBirthdayScreen
import com.example.actualizar.ui.screens.profile.ChangeEquipmentScreen
import com.example.actualizar.ui.screens.profile.ChangeExperienceScreen
import com.example.actualizar.ui.screens.profile.ChangeGenderScreen
import com.example.actualizar.ui.screens.profile.ChangeGoalScreen
import com.example.actualizar.ui.screens.profile.ChangeHeightScreen
import com.example.actualizar.ui.screens.profile.DeleteAccountConfirmScreen
import com.example.actualizar.ui.screens.profile.DeleteAccountScreen


@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = AppDestinations.Profile.route
    ) {

        composable(AppDestinations.Profile.route) {
            ProfileScreen(navController)
        }

        composable(AppDestinations.UpdateAccount.route) {
            UpdateAccountScreen(navController)
        }
        composable("change_username") {
            ChangeUsernameScreen(navController)
        }
        composable("change_phone"){
            ChangePhoneScreen(navController)
        }
        composable("change_email") {
            ChangeEmailScreen(navController)
        }
        composable(AppDestinations.UpdateBirthday.route) {
            ChangeBirthdayScreen(navController)
        }

        composable(AppDestinations.UpdateGender.route) {
            ChangeGenderScreen(navController)
        }

        composable(AppDestinations.UpdateHeight.route) {
            ChangeHeightScreen(navController)
        }

        composable(AppDestinations.UpdateExperience.route) {
            ChangeExperienceScreen(navController)
        }

        composable(AppDestinations.UpdateGoal.route){
            ChangeGoalScreen(navController)
        }

        composable(AppDestinations.UpdateEquipment.route){
            ChangeEquipmentScreen(navController)
        }

        composable(AppDestinations.DeleteAccount.route){
            DeleteAccountScreen(navController)
        }

        composable("delete_account_confirm") {
            DeleteAccountConfirmScreen(navController, expectedUsername = "tuUsuario") { typed ->
                // TODO: aquí llamas a tu ViewModel/API para eliminar con 'typed'
            }
        }

    }
}
