package com.shojishunsuke.kibunnsns.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.preference.PreferenceFragmentCompat
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.shojishunsuke.kibunnsns.R

class PreferenceFragment : PreferenceFragmentCompat() {

    companion object {
        private const val RC_SIGN_IN = 123
        private const val RESULT_OK = -1
        private const val TAG = "GoogleAuth"
    }

    private val user = FirebaseAuth.getInstance().currentUser

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preference, rootKey)

        val authPreference = findPreference("authenticate")
        authPreference.setOnPreferenceClickListener {
            startActivityForResult(
                AuthUI.getInstance().createSignInIntentBuilder().setAvailableProviders(
                    listOf(AuthUI.IdpConfig.GoogleBuilder().build(),
                        AuthUI.IdpConfig.EmailBuilder().build(),
                        AuthUI.IdpConfig.PhoneBuilder().build())
                ).build(), RC_SIGN_IN
            )
            true
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
//           TODO  GoogleAccountへのログインで落ちる error: com.firebase.ui.auth.FirebaseUiException: Code: 12500, message: 12500
            val response = IdpResponse.fromResultIntent(data)
            if (resultCode == RESULT_OK) {
                fireBaseAuthWithGoogle(response!!.idpToken!!)
            }
        }
    }


    private fun fireBaseAuthWithGoogle(idToken:String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)

        user?.linkWithCredential(credential)?.addOnCompleteListener(requireActivity()){ task ->
            if (task.isSuccessful) {
                Log.d(TAG, "signInWithCredential:success")
            } else {
                Log.d(TAG, "signInWithCredential : failure")
            }

        }

    }
}