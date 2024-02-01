import java.time.Instant

data class ServiceContract(
    val id: Int,
    val partyA: Party,
    val partyB: Party,
)

data class Party(
    val householdName: String,
    val service: String,
    val agreedAt: Instant? = null,
)

fun ServiceContract.isHouseholdInvolved(householdName: String): Boolean =
    partyB.householdName == householdName ||
        partyA.householdName == householdName

fun ServiceContract.bothPartiesHaveDifferentNames(): Boolean = partyB.householdName != partyA.householdName

fun ServiceContract.getParty(householdName: String): Party? =
    if (partyA.householdName == householdName) {
        partyA
    } else if (partyB.householdName == householdName) {
        partyB
    } else {
        null
    }

fun ServiceContract.serviceReceivedBy(householdName: String): String? = getParty(householdName)?.service

fun ServiceContract.withReceivedService(
    householdName: String,
    service: String,
): ServiceContract =
    if (partyA.householdName == householdName) {
        copy(partyA = partyA.copy(service = service))
    } else if (partyB.householdName == householdName) {
        copy(partyB = partyB.copy(service = service))
    } else {
        this
    }

fun ServiceContract.partyAgreed(householdName: String): Boolean =
    (partyA.householdName == householdName && partyA.agreedAt != null) ||
        (partyB.householdName == householdName && partyB.agreedAt != null)

fun ServiceContract.agree(
    householdName: String,
    timeSupplier: () -> Instant,
): ServiceContract =
    if (
        partyA.householdName == householdName &&
        partyA.agreedAt == null
    ) {
        copy(partyA = partyA.copy(agreedAt = timeSupplier()))
    } else if (
        partyB.householdName == householdName &&
        partyB.agreedAt == null
    ) {
        copy(partyB = partyB.copy(agreedAt = timeSupplier()))
    } else {
        this
    }
