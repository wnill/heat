package de.wnill.heat.util.persistence;

import java.util.List;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.RegionUtils;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;

import de.wnill.heat.util.dto.Reading;
import de.wnill.heat.util.dto.Sensor;

public class PersistenceService {

  private static PersistenceService instance;

  private static AmazonDynamoDBClient client =
      new AmazonDynamoDBClient(new ProfileCredentialsProvider());

  private static DynamoDBMapper mapper = null;

  private PersistenceService() {

  }

  /**
   * Singleton access.
   * 
   * @return instance
   */
  public static PersistenceService getInstance() {
    if (instance == null) {
      instance = new PersistenceService();
      client.setRegion(Region.getRegion(Regions.EU_CENTRAL_1));
      mapper = new DynamoDBMapper(client);
    }
    return instance;
  }

  /**
   * Persists given object.
   * 
   * @param data the object to persist
   * @return false if something went wrong
   */
  public boolean store(Object data) {
    try {
      mapper.save(data);
    } catch (AmazonClientException e) {
      return false;
    }
    return true;
  }

  /**
   * Loads a persisted reading.
   * 
   * @param id the reading id
   * @param timestamp of the reading
   * @return the reading
   */
  public Reading loadReading(String id, String timestamp) {
    try {
      return mapper.load(Reading.class, id, timestamp);
    } catch (AmazonClientException e) {
      return null;
    }
  }

  /**
   * Loads a persisted sensor.
   * 
   * @param id the sensor id
   * @return the sensor
   */
  public Sensor loadSensor(String id) {
    try {
      return mapper.load(Sensor.class, id);
    } catch (AmazonClientException e) {
      return null;
    }
  }
}
