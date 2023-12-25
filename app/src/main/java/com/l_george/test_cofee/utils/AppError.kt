package com.l_george.test_cofee.utils

sealed class AppError(override var message:String) : RuntimeException()

class NetworkError : AppError(NETWORK_ERROR_MESSAGE)
class UnknownError : AppError(UNKNOWN_ERROR_MESSAGE)
class ApiError : AppError(API_ERROR_MESSAGE)
class AuthError : AppError(AUTH_ERROR_MESSAGE)

private const val NETWORK_ERROR_MESSAGE = "Ошибка соединения. Проверьте подключение к сети."
private const val UNKNOWN_ERROR_MESSAGE = "Неизвестная ошибка"
private const val API_ERROR_MESSAGE = "Ошибка на сервере. Попробуйте позже."
private const val AUTH_ERROR_MESSAGE = "Ошибка аутентификации. Войдите в свой аккаунт."