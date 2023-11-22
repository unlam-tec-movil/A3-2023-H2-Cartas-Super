package ar.edu.unlam.mobile.scaffold.data.qrmanager

class HeroQrManagerException(message: String? = null, cause: Throwable? = null) : Exception(message, cause) {
    constructor(cause: Throwable) : this(null, cause)
}
