package ar.edu.unlam.mobile.scaffold.domain.sensor

class OrientationDataManagerException(message: String? = null, cause: Throwable? = null) : Exception(message, cause) {
    constructor(cause: Throwable) : this(null, cause)
}
