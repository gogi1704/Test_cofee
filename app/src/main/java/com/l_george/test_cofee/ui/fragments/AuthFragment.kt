package com.l_george.test_cofee.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.l_george.test_cofee.R
import com.l_george.test_cofee.app.CoffeeApp
import com.l_george.test_cofee.data.models.UserModel
import com.l_george.test_cofee.databinding.FragmentAuthBinding
import com.l_george.test_cofee.ui.viewModels.authViewModel.AuthViewModel
import com.l_george.test_cofee.ui.viewModels.authViewModel.AuthViewModelFactory
import javax.inject.Inject

class AuthFragment : Fragment() {
    private lateinit var binding: FragmentAuthBinding

    @Inject
    lateinit var authViewModelFactory: AuthViewModelFactory
    private lateinit var authViewModel: AuthViewModel
    private lateinit var activity: MainActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireContext().applicationContext as CoffeeApp).component.inject(this)
        authViewModel = ViewModelProvider(this, authViewModelFactory)[AuthViewModel::class.java]
        activity = requireActivity() as MainActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAuthBinding.inflate(layoutInflater, container, false)
        registerMode()


        with(binding) {

            inputEmail.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
                if (!hasFocus && !inputEmail.isEmailValid()) {
                    textInputLayoutEmail.error = getString(R.string.check_email_error)
                } else {
                    textInputLayoutEmail.error = null
                }
            }

            inputPassword.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
                if (!hasFocus && inputPassword.text.toString().length < 5) {
                    textInputLayoutPassword.error = getString(R.string.check_pass_error)
                } else {
                    textInputLayoutPassword.error = null
                }
            }


            inputPasswordRepeat.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
                if (!hasFocus && inputPasswordRepeat.text.toString() != inputPassword.text.toString()) {
                    textInputLayoutPasswordRepeat.error = getString(R.string.check_pass_repeat)
                } else {
                    textInputLayoutPasswordRepeat.error = null
                }
            }



            buttonLogin.setOnClickListener {
                if (buttonLogin.text.toString() == getString(R.string.register)) {
                    registerMode()
                } else {
                    loginMode()
                }
            }
            buttonComplete.setOnClickListener {
                clearFocusFromChildren(layout)
                if (buttonComplete.text == getString(R.string.register)) {
                    if (validateData()
                    ) {
                        authViewModel.register(
                            UserModel(
                                inputEmail.text.toString(),
                                inputPassword.text.toString()
                            )
                        )
                    } else {
                        makeToast(getString(R.string.checkAll))
                    }
                } else {
                    if (validateData()) {
                        authViewModel.logIn(
                            UserModel(
                                inputEmail.text.toString(),
                                inputPassword.text.toString()
                            )
                        )
                    } else {
                        makeToast(getString(R.string.checkAll))
                    }
                }
            }


            authViewModel.isAuthLiveData.observe(viewLifecycleOwner) {
                if (it != null && it) {
                    findNavController().navigate(R.id.action_authFragment_to_coffeeListFragment)
                }
            }
        }


        return binding.root
    }

    private fun registerMode() {
        with(binding) {
            buttonLogin.text = getString(R.string.login)
            textInputLayoutPasswordRepeat.visibility = View.VISIBLE
            textRepeatPass.visibility = View.VISIBLE
            buttonComplete.text = getString(R.string.register)
            activity.topBarSettings(false, getString(R.string.register))


        }
    }

    private fun loginMode() {
        with(binding) {
            activity.topBarSettings(false, getString(R.string.login))

            buttonLogin.text = getString(R.string.register)
            buttonComplete.text = getString(R.string.login)
            textInputLayoutPasswordRepeat.visibility = View.GONE
            textRepeatPass.visibility = View.GONE
            textRepeatPass.text = ""
            textInputLayoutPasswordRepeat.error = null
        }
    }

    private fun validateData(): Boolean {
        var isValidate: Boolean
        with(binding) {

            isValidate = textInputLayoutEmail.error == null &&
                    textInputLayoutEmail.error == null &&
                    textInputLayoutPasswordRepeat.error == null &&
                    inputEmail.text.toString().isNotEmpty() &&
                    inputPassword.text.toString().isNotEmpty()
        }
        return isValidate
    }

    private fun makeToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun EditText.isEmailValid(): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(this.text.toString()).matches()
    }

    private fun clearFocusFromChildren(view: View) {
        if (view is ViewGroup) {
            for (i in 0 until view.childCount) {
                clearFocusFromChildren(view.getChildAt(i))
            }
        } else (view as? EditText)?.clearFocus()

    }


}