import software.amazon.awssdk.auth.credentials.AnonymousCredentialsProvider
import software.amazon.awssdk.core.sync.RequestBody
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.S3Configuration
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import software.amazon.awssdk.services.s3.model.StorageClass
import java.net.URI

fun main() {
    val config = S3Configuration.builder()
        .pathStyleAccessEnabled(true)
        .build()
    val client = S3Client.builder()
        .credentialsProvider(AnonymousCredentialsProvider.create())
        .serviceConfiguration(config)
        .endpointOverride(URI("http://localhost:9000"))
        .region(Region.of("minio"))
        .build()
    val bytes = byteArrayOf(0xCA.toByte(), 0xFE.toByte(), 0xBA.toByte(), 0xBe.toByte())
    val request = PutObjectRequest.builder()
        .bucket("test")
        .key("cache/1231321312312")
        .contentLength(bytes.size.toLong())
        .contentType("application/vnd.gradle.build-cache-artifact")
        .storageClass(StorageClass.REDUCED_REDUNDANCY)
        .build()

    val response = client.putObject(request, RequestBody.fromBytes(bytes))
    println("ETag: ${response.eTag()}")
}