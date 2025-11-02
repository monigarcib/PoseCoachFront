package com.example.actualizar.ui.navigation

sealed class AppDestinations(val route: String) {
    object Profile : AppDestinations("profile")
    object UpdateAccount : AppDestinations("update_account")
    object UpdateBirthday : AppDestinations("update_birthday")
    object UpdateGender : AppDestinations("update_gender")
    object UpdateHeight : AppDestinations("update_height")
    object UpdateExperience : AppDestinations("update_experience")
    object UpdateGoal : AppDestinations("update_goal")
    object UpdateEquipment : AppDestinations("update_equipment")
    object DeleteAccount : AppDestinations("delete_account")
    object DeleteAccountConfirm : AppDestinations("delete_account_confirm")

    object ChangeUserName: AppDestinations("change_username")

    object ChangePhone: AppDestinations("change_phone")


}
