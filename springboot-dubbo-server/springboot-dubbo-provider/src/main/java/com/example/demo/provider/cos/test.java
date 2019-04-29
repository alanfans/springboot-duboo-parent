package com.example.demo.provider.cos;

import com.ibm.cloud.objectstorage.ClientConfiguration;
import com.ibm.cloud.objectstorage.SDKGlobalConfiguration;
import com.ibm.cloud.objectstorage.SdkClientException;
import com.ibm.cloud.objectstorage.auth.AWSCredentials;
import com.ibm.cloud.objectstorage.auth.AWSStaticCredentialsProvider;
import com.ibm.cloud.objectstorage.client.builder.AwsClientBuilder;
import com.ibm.cloud.objectstorage.oauth.BasicIBMOAuthCredentials;
import com.ibm.cloud.objectstorage.services.s3.AmazonS3;
import com.ibm.cloud.objectstorage.services.s3.AmazonS3ClientBuilder;
import com.ibm.cloud.objectstorage.services.s3.model.Bucket;

import java.util.List;

public class test {
    private static String COS_ENDPOINT = "https://control.cloud-object-storage.cloud.ibm.com/v2/endpoints"; // eg "https://s3.us.cloud-object-storage.appdomain.cloud"
    private static String COS_API_KEY_ID = "Qosijhm1Rl80mlKKNGXFIBamioYJWMbMRNXfPBRjf9vP"; // eg "0viPHOY7LbLNa9eLftrtHPpTjoGv6hbLD1QalRXikliJ"
    private static String COS_AUTH_ENDPOINT = "https://iam.cloud.ibm.com/oidc";
    private static String COS_SERVICE_CRN = "crn:v1:bluemix:public:cloud-object-storage:global:a/fb6c3fcac14b486d9a7a6864e5cfc299:576aa5c5-42dc-471b-a0cc-027757854fac::"; // "crn:v1:bluemix:public:iam-identity::a/3ag0e9402tyfd5d29761c3e97696b71n::serviceid:ServiceId-540a4a41-7322-4fdd-a9e7-e0cb7ab760f9"
    private static String COS_BUCKET_LOCATION = "us"; // eg "us"

    private static AmazonS3 _cos;
    public static void main(String[] args) {
        SDKGlobalConfiguration.IAM_ENDPOINT = COS_AUTH_ENDPOINT;
        try {
            _cos = createClient(COS_API_KEY_ID, COS_SERVICE_CRN, COS_ENDPOINT, COS_BUCKET_LOCATION);

            final List<Bucket> bucketList = _cos.listBuckets();
            for (final Bucket bucket : bucketList) {
                System.out.printf("Bucket Name: %s\n", bucket.getName());
            }
        } catch (SdkClientException sdke) {
            System.out.printf("SDK Error: %s\n", sdke.getMessage());
        } catch (Exception e) {
            System.out.printf("Error: %s\n", e.getMessage());
        }
    }

    public static AmazonS3 createClient(String api_key, String service_instance_id, String endpoint_url, String location) {
        AWSCredentials credentials = new BasicIBMOAuthCredentials(api_key, service_instance_id);
        ClientConfiguration clientConfig = new ClientConfiguration().withRequestTimeout(5000);
        clientConfig.setUseTcpKeepAlive(true);

        AmazonS3 cos = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(endpoint_url, location)).withPathStyleAccessEnabled(true)
                .withClientConfiguration(clientConfig).build();

        return cos;
    }
}