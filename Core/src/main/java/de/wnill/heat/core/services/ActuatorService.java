package de.wnill.heat.core.services;

import java.util.ArrayList;
import java.util.List;

import de.wnill.heat.util.dto.Actuator;
import de.wnill.heat.util.persistence.PersistenceService;

public class ActuatorService {

  private ArrayList<Actuator> actuators = new ArrayList<Actuator>();

  private static ActuatorService instance = null;

  /**
   * Returns the singleton instance.
   * 
   * @return instance
   */
  public static ActuatorService getInstance() {
    if (instance == null) {
      instance = new ActuatorService();
    }
    return instance;
  }

  /**
   * Registers an actuator if not already existing yet.
   * @param id the actuator's id
   */
  public void registerActuator(String id) {
    
    for (Actuator actuator : actuators) {
      if (actuator.getId().equals(id)) {
        return;
      }
    }
    
    Actuator existing = PersistenceService.getInstance().loadEntity(id, Actuator.class);
    if (existing == null) {
      PersistenceService.getInstance().store(new Actuator(id));
    }
    actuators.add(new Actuator(id));
  }

  public List<Actuator> getAll() {
    return actuators;
  }

  /**
   * Returns a single Actuator for given id.
   * @param id the id to check
   * @return an Actuator
   */
  public Actuator getById(String id) {
    for (Actuator actuator : actuators) {
      if (actuator.getId().equals(id)) {
        return actuator;
      }
    }
    
    // If not cached, check if it exists in DB
    Actuator actuator = PersistenceService.getInstance().loadEntity(id, Actuator.class);
    return actuator;
  }

}