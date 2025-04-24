package ru.hse.coursework.godaily.core.data.network

const val ROUTE_BASE_PATH_URL = "/api/data-provider/routes"
const val ADD_ROUTE_URL = "/user/add"
const val GET_DRAFTS_URL = "/user/drafts"
const val GET_PUBLISHED_URL = "/user/published"
const val DELETE_ROUTE_URL = "/user/delete"
const val GET_ROUTE_PAGE_URL = "/user/page"
const val GET_ROUTE_BY_SEARCH_VALUE_URL = "/user/search"
const val GET_ROUTES_URL = "/user/all"

const val FAVORITE_BASE_PATH_URL = "/api/data-provider/favorites"
const val GET_FAVOURITES_URL = "/user/all"
const val ADD_FAVOURITE_URL = "/user/add"
const val DELETE_FAVOURITE_URL = "/user/delete"

const val SESSION_BASE_PATH_URL = "/api/data-provider/sessions"
const val GET_FINISHED_URL = "/user/finished"
const val GET_UNFINISHED_URL = "/user/unfinished"
const val ADD_SESSION_URL = "/user/add"
const val GET_SESSION_URL = "/user"

const val REVIEW_BASE_PATH_URL = "/api/data-provider/reviews"
const val GET_REVIEWS_URL = "/user/all"
const val ADD_REVIEW_URL = "/user/add"

const val USER_BASE_PATH_URL = "/api/security-service/security"
const val REGISTER_URL = "/register"
const val LOGIN_URL = "/login"
const val GET_USER_INFO_URL = "/info"
const val UPDATE_USERNAME_URL = "/update-username"
const val UPDATE_USER_PHOTO_URL = "/update-photo"

const val PHOTO_BASE_PATH_URL = "/api/data-provider/photos/user"
const val UPLOAD_PHOTO_URL = "/upload"