package com.example.demo.provider.cos;

import com.ibm.cloud.objectstorage.ClientConfiguration;
import com.ibm.cloud.objectstorage.SDKGlobalConfiguration;
import com.ibm.cloud.objectstorage.auth.AWSCredentials;
import com.ibm.cloud.objectstorage.auth.AWSStaticCredentialsProvider;
import com.ibm.cloud.objectstorage.client.builder.AwsClientBuilder;
import com.ibm.cloud.objectstorage.oauth.BasicIBMOAuthCredentials;
import com.ibm.cloud.objectstorage.services.s3.AmazonS3;
import com.ibm.cloud.objectstorage.services.s3.AmazonS3ClientBuilder;
import com.ibm.cloud.objectstorage.services.s3.model.Bucket;
import com.ibm.cloud.objectstorage.services.s3.model.ListObjectsRequest;
import com.ibm.cloud.objectstorage.services.s3.model.ObjectListing;
import com.ibm.cloud.objectstorage.services.s3.model.S3ObjectSummary;

import java.sql.Timestamp;
import java.util.List;

public class CosExample {

    private static AmazonS3 _cosClient;

    /**
     * @param args
     */
    public static void main(String[] args)
    {

        SDKGlobalConfiguration.IAM_ENDPOINT = "https://iam.cloud.ibm.com/oidc/token";

        String bucketName = "alan-001";  // eg my-unique-bucket-name
        // eg "W00YiRnLW4a3fTjMB-oiB-2ySfTrFBIQQWanc--P3byk"
        String newBucketName = "alan-002";
        String api_key = "Qosijhm1Rl80mlKKNGXFIBamioYJWMbMRNXfPBRjf9vP";
        // eg "crn:v1:bluemix:public:cloud-object-storage:global:a/3bf0d9003abfb5d29761c3e97696b71c:d6f04d83-6c4f-4a62-a165-696756d63903::"
        String service_instance_id = "crn:v1:bluemix:public:cloud-object-storage:global:a/fb6c3fcac14b486d9a7a6864e5cfc299:576aa5c5-42dc-471b-a0cc-027757854fac::";
        String endpoint_url = "https://control.cloud-object-storage.cloud.ibm.com/v2/endpoints";
        String storageClass = "us-south-standard";
        String location = "us";

        System.out.println("Current time: " + new Timestamp(System.currentTimeMillis()).toString());
        _cosClient = createClient(api_key, service_instance_id, endpoint_url, location);

        listObjects(bucketName, _cosClient);
        createBucket(newBucketName, _cosClient);
        listBuckets(_cosClient);
    }

    /**
     * @param api_key
     * @param service_instance_id
     * @param endpoint_url
     * @param location
     * @return AmazonS3
     */
    public static AmazonS3 createClient(String api_key, String service_instance_id, String endpoint_url, String location)
    {
        AWSCredentials credentials;
        credentials = new BasicIBMOAuthCredentials(api_key, service_instance_id);

        ClientConfiguration clientConfig = new ClientConfiguration().withRequestTimeout(5000);
        clientConfig.setUseTcpKeepAlive(true);

        AmazonS3 cosClient = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(endpoint_url, location)).withPathStyleAccessEnabled(true)
                .withClientConfiguration(clientConfig).build();
        return cosClient;
    }

    /**
     * @param bucketName
     * @param cosClient
     */
    public static void listObjects(String bucketName, AmazonS3 cosClient)
    {
        System.out.println("Listing objects in bucket " + bucketName);
        ObjectListing objectListing = cosClient.listObjects(new ListObjectsRequest().withBucketName(bucketName));
        for (S3ObjectSummary objectSummary : objectListing.getObjectSummaries()) {
            System.out.println(" - " + objectSummary.getKey() + "  " + "(size = " + objectSummary.getSize() + ")");
        }
        System.out.println();
    }

    /**
     * @param bucketName
     * @param cosClient
     */
    public static void createBucket(String bucketName, AmazonS3 cosClient)
    {
//        cosClient.createBucket(bucketName, storageClass);
        cosClient.createBucket(bucketName);
    }

    /**
     * @param cosClient
     */
    public static void listBuckets(AmazonS3 cosClient)
    {
        System.out.println("Listing buckets");
        final List<Bucket> bucketList = _cosClient.listBuckets();
        for (final Bucket bucket : bucketList) {
            System.out.println(bucket.getName());
        }
        System.out.println();
    }
}
