package com.sopt.famfam.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.sopt.famfam.R
import kotlinx.android.synthetic.main.activity_certification.*
import org.jetbrains.anko.startActivity
import android.text.TextUtils
import android.util.Log
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import java.util.concurrent.TimeUnit

class CertificationActivity : AppCompatActivity(), View.OnClickListener {

    // [START declare_auth]
    private lateinit var auth: FirebaseAuth
    // [END declare_auth]

    private var verificationInProgress = false
    private var storedVerificationId: String? = ""
    private lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_certification)

        Log.d("uuuu1", "1")
        // Restore instance state
        if (savedInstanceState != null) {
            onRestoreInstanceState(savedInstanceState)
        }
        Log.d("uuuu1","2")

        // Assign click listeners
        tv_certification_act_request_code_btn11.setOnClickListener(this)
        tv_certification_act_complete_btn.setOnClickListener(this)
        btn_resend_code.setOnClickListener(this)
//        signOutButton.setOnClickListener(this)

        // [START initialize_auth]
        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()
        // [END initialize_auth]

        // Initialize phone auth callbacks
        // [START phone_auth_callbacks]
        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                Log.v("uuuu1", "onVerificationCompleted")
                // This callback will be invoked in two situations:
                // 1 - Instant verification. In some cases the phone number can be instantly
                //     verified without needing to send or enter a verification code.
                // 2 - Auto-retrieval. On some devices Google Play services can automatically
                //     detect the incoming verification SMS and perform verification without
                //     user action.
                Log.d("uuuu1", "핸드폰 인증 성공:$credential")
                // [START_EXCLUDE silent]
                verificationInProgress = false
                // [END_EXCLUDE]

                // [START_EXCLUDE silent]
                // Update the UI and attempt sign in with the phone credential
                updateUI(STATE_VERIFY_SUCCESS, credential)
                // [END_EXCLUDE]
                signInWithPhoneAuthCredential(credential)
            }

            override fun onVerificationFailed(e: FirebaseException) {
                Log.v("uuuu1", "onVerificationFailed")
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.
                Log.w("uuuu1", "핸드폰 인증 실패", e)
                // [START_EXCLUDE silent]
                verificationInProgress = false
                // [END_EXCLUDE]

                if (e is FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                    // [START_EXCLUDE]
                    et_certification_act_input_phone_number.error = "Invalid phone number."
                    // [END_EXCLUDE]
                } else if (e is FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                    // [START_EXCLUDE]
//                    Snackbar.make(findViewById(android.R.id.content), "Quota exceeded.",
//                            Snackbar.LENGTH_SHORT).show()
                    // [END_EXCLUDE]
                }

                // Show a message and update the UI
                // [START_EXCLUDE]
                updateUI(STATE_VERIFY_FAILED)
                // [END_EXCLUDE]
            }

            override fun onCodeSent(verificationId: String?, token: PhoneAuthProvider.ForceResendingToken) {
                Log.v("uuuu1", "onCodeSent")
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                Log.d("uuuu1", "핸드폰 코드 보내기" + verificationId!!)

                // Save verification ID and resending token so we can use them later
                storedVerificationId = verificationId
                resendToken = token

                // [START_EXCLUDE]
                // Update UI
                updateUI(STATE_CODE_SENT)
                // [END_EXCLUDE]
            }
        }
        // [END phone_auth_callbacks]

//        setOnBtnClickListener()
    }

    // [START on_start_check_user]
    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        updateUI(currentUser)

        // [START_EXCLUDE]
        if (verificationInProgress && validatePhoneNumber()) {
            val number: String = et_certification_act_input_phone_number.text.toString()
            val phoneNumber: String = "+82" + number.substring(1, 10)
            startPhoneNumberVerification(phoneNumber)
            Log.v("uuuu1", "onStart1")
        }
        Log.v("uuuu1", et_certification_act_input_phone_number.text.toString())
        Log.v("uuuu1", "onStart2")
        // [END_EXCLUDE]
    }

    // [END on_start_check_user]
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(KEY_VERIFY_IN_PROGRESS, verificationInProgress)
        Log.v("uuuu1", "onSaveInstanceState")
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        verificationInProgress = savedInstanceState.getBoolean(KEY_VERIFY_IN_PROGRESS)
        Log.v("uuuu1", "onRestoreInstanceState")
    }

    private fun startPhoneNumberVerification(phoneNumber: String) {
        // [START start_phone_auth]
        Log.d("핸드폰 번호1", phoneNumber)
        Log.v("uuuu1", "startPhoneNumberVerification")
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
            phoneNumber,      // Phone number to verify
            60,               // Timeout duration
            TimeUnit.SECONDS, // Unit of timeout
            this,             // Activity (for callback binding)
            callbacks
        ) // OnVerificationStateChangedCallbacks
        // [END start_phone_auth]

        verificationInProgress = true
    }

    private fun verifyPhoneNumberWithCode(verificationId: String?, code: String) {
        Log.v("uuuu1", "verifyPhoneNumberWithCode")
        // [START verify_with_code]
        val credential = PhoneAuthProvider.getCredential(verificationId!!, code)
        // [END verify_with_code]
        signInWithPhoneAuthCredential(credential)
    }

    // [START resend_verification]
    private fun resendVerificationCode(phoneNumber: String, token: PhoneAuthProvider.ForceResendingToken?) {
        Log.v("uuuu1", "resendVerificationCode")
        Log.d("핸드폰번호2", phoneNumber)
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
            phoneNumber, // Phone number to verify
            60, // Timeout duration
            TimeUnit.SECONDS, // Unit of timeout
            this, // Activity (for callback binding)
            callbacks, // OnVerificationStateChangedCallbacks
            token
        ) // ForceResendingToken from callbacks
    }
    // [END resend_verification]

    // [START sign_in_with_phone]
    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        Log.v("uuuu1", "signInWithPhoneAuthCredential")
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("uuuu1", "핸드폰 가입 성공:success")

                    val user = task.result?.user
                    // [START_EXCLUDE]
                    updateUI(STATE_SIGNIN_SUCCESS, user)
                    // [END_EXCLUDE]
                } else {
                    // Sign in failed, display a message and update the UI
                    Log.w("uuuu1", "핸드폰 가입 실패:failure", task.exception)
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        // The verification code entered was invalid
                        // [START_EXCLUDE silent]
                        et_certification_act_input_code.error = "Invalid code."
                        // [END_EXCLUDE]
                    }
                    // [START_EXCLUDE silent]
                    // Update UI
                    updateUI(STATE_SIGNIN_FAILED)
                    // [END_EXCLUDE]
                }
            }
    }
    // [END sign_in_with_phone]

    private fun signOut() {
        auth.signOut()
        updateUI(STATE_INITIALIZED)

    }

    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            updateUI(STATE_SIGNIN_SUCCESS, user)
        } else {
            updateUI(STATE_INITIALIZED)
        }
    }

    private fun updateUI(uiState: Int, cred: PhoneAuthCredential) {
        updateUI(uiState, null, cred)
    }

    private fun updateUI(uiState: Int, user: FirebaseUser? = auth.currentUser, cred: PhoneAuthCredential? = null) {
        Log.v("uuuu1", "updateUI")
        when (uiState) {
            STATE_INITIALIZED -> {
                // Initialized state, show only the phone number field and start button
                enableViews(tv_certification_act_request_code_btn_text11, et_certification_act_input_phone_number)
                disableViews(tv_certification_act_request_code_btn11, btn_resend_code, et_certification_act_input_code)
//                detail.text = null
            }
            STATE_CODE_SENT -> {
                // Code sent state, show the verification field, the
                enableViews(
                    tv_certification_act_request_code_btn_text11,
                    btn_resend_code,
                    et_certification_act_input_phone_number,
                    et_certification_act_input_code
                )
                disableViews(tv_certification_act_request_code_btn11)
//                detail.setText(R.string.status_code_sent)
            }
            STATE_VERIFY_FAILED -> {
                // Verification has failed, show all options
                enableViews(
                    tv_certification_act_request_code_btn_text11,
                    tv_certification_act_request_code_btn11,
                    btn_resend_code,
                    et_certification_act_input_phone_number,
                    et_certification_act_input_code
                )
//                detail.setText(R.string.status_verification_failed)
            }
            STATE_VERIFY_SUCCESS -> {
                // Verification has succeeded, proceed to firebase sign in
                disableViews(
                    tv_certification_act_request_code_btn_text11,
                    tv_certification_act_request_code_btn11,
                    btn_resend_code,
                    et_certification_act_input_phone_number,
                    et_certification_act_input_code
                )
//                detail.setText(R.string.status_verification_succeeded)

                // Set the verification text based on the credential
                if (cred != null) {
                    if (cred.smsCode != null) {
                        et_certification_act_input_code.setText(cred.smsCode)
                    } else {
                        et_certification_act_input_code.setText(R.string.instant_validation)
                    }
                }
            }
            STATE_SIGNIN_FAILED ->
                // No-op, handled by sign-in check
//                detail.setText(R.string.status_sign_in_failed)
                Log.d("uuuu1", "signin failed")
            STATE_SIGNIN_SUCCESS -> {
                Log.d("uuuu1", "signin success")
            }
        } // Np-op, handled by sign-in check
//
//        if (user == null) {
//            // Signed out
//            phoneAuthFields.visibility = View.VISIBLE
//            signedInButtons.visibility = View.GONE
//
//            status.setText(R.string.signed_out)
//        } else {
//            // Signed in
//            phoneAuthFields.visibility = View.GONE
//            signedInButtons.visibility = View.VISIBLE
//
//            enableViews(fieldPhoneNumber, fieldVerificationCode)
//            fieldPhoneNumber.text = null
//            fieldVerificationCode.text = null
//
//            status.setText(R.string.signed_in)
//            detail.text = getString(R.string.firebase_status_fmt, user.uid)
//        }
    }

    private fun validatePhoneNumber(): Boolean {
        Log.v("uuuu1", "validatePhoneNumber")
        val phoneNumber = et_certification_act_input_phone_number.text.toString()
        if (TextUtils.isEmpty(phoneNumber)) {
            et_certification_act_input_phone_number.error = "Invalid phone number."
            return false
        }
        return true
    }

    private fun enableViews(vararg views: View) {
        Log.v("uuuu1", "enableViews")
        for (v in views) {
            v.isEnabled = true
        }
    }

    private fun disableViews(vararg views: View) {
        Log.v("uuuu1", "disableViews")
        for (v in views) {
            v.isEnabled = false
        }
    }

    override fun onClick(view: View) {
        val number: String = et_certification_act_input_phone_number.text.toString()
        val phoneNumber: String = "+82" + number.substring(1, 11)
        when (view.id) {
            R.id.tv_certification_act_request_code_btn11 -> {

                Log.v("uuuu1", "tv_certification_act_request_code_btn_text")
                if (!validatePhoneNumber()) {
                    return
                }
                et_certification_act_input_code.requestFocus()
                certification_act_request_code_layout.visibility = View.VISIBLE
                tv_certification_act_complete_btn.visibility = View.VISIBLE
                tv_certification_act_request_code_btn11.visibility = View.GONE

                startPhoneNumberVerification(phoneNumber)
                Log.d("uuuu1", phoneNumber)
            }
            R.id.tv_certification_act_complete_btn -> {
                val code = et_certification_act_input_code.text.toString()
                val phoneNumber : String = et_certification_act_input_phone_number.text.toString()
                startActivity<SignupActivity>("phoneNumber" to phoneNumber)
                if (TextUtils.isEmpty(code)) {
                    et_certification_act_input_code.error = "Cannot be empty."
                    return
                }

                verifyPhoneNumberWithCode(storedVerificationId, code)
            }

            R.id.btn_resend_code -> resendVerificationCode(phoneNumber, resendToken)
//            R.id.signOutButton -> signOut()
        }
    }

    companion object {
        private const val TAG = "PhoneAuthActivity"
        private const val KEY_VERIFY_IN_PROGRESS = "key_verify_in_progress"
        private const val STATE_INITIALIZED = 1
        private const val STATE_VERIFY_FAILED = 3
        private const val STATE_VERIFY_SUCCESS = 4
        private const val STATE_CODE_SENT = 2
        private const val STATE_SIGNIN_FAILED = 5
        private const val STATE_SIGNIN_SUCCESS = 6
    }

}
