/**
 *  Copyright 2010 Society for Health Information Systems Programmes, India (HISP India)
 *
 *  This file is part of Hospital-core module.
 *
 *  Hospital-core module is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.

 *  Hospital-core module is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Hospital-core module.  If not, see <http://www.gnu.org/licenses/>.
 *
 **/


package org.openmrs.module.hospitalcore;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.openmrs.Concept;
import org.openmrs.Encounter;
import org.openmrs.EncounterType;
import org.openmrs.Obs;
import org.openmrs.Patient;

import org.openmrs.Person;
import org.openmrs.PersonAddress;
import org.openmrs.PersonAttribute;
import org.openmrs.PersonAttributeType;
import org.openmrs.User;
import org.openmrs.api.APIException;
import org.openmrs.api.ConceptService;
import org.openmrs.api.OpenmrsService;
import org.openmrs.module.hospitalcore.model.CoreForm;
import org.openmrs.module.hospitalcore.model.OpdTestOrder;
import org.openmrs.module.hospitalcore.model.PatientSearch;
import org.springframework.transaction.annotation.Transactional;
import org.xml.sax.SAXException;

@Transactional
public interface HospitalCoreService extends OpenmrsService {

	public List<Obs> listObsGroup(Integer personId, Integer conceptId,
			Integer min, Integer max) throws APIException;

	public EncounterType insertEncounterTypeByKey(String type)
			throws APIException;

	public void creatConceptQuestionAndAnswer(ConceptService conceptService,
			User user, String conceptParent, String... conceptChild)
			throws APIException;

	public Obs createObsGroup(Patient patient, String properyKey);

	public Concept insertConceptUnlessExist(String dataTypeName,
			String conceptClassName, String conceptName) throws APIException;

	public Obs getObsGroup(Patient patient);

	public Obs getObsGroupCurrentDate(Integer personId) throws APIException;

	public void insertSynonym(Concept concept, String name);

	public void insertMapping(Concept concept, String sourceName,
			String sourceCode);

	/**
	 * Insert the concept unless it exists
	 * 
	 * @param dataTypeName
	 *            name of datatype
	 * @param conceptClassName
	 *            name of concept class
	 * @param name
	 *            name of the concept
	 * @param shortname
	 *            shortname of the concept
	 * @param description
	 *            description of the concept
	 * @return the created concept or the existing concept
	 * @throws APIException
	 */
	public Concept insertConcept(String dataTypeName, String conceptClassName,
			String name, String shortname, String description)
			throws APIException;

	/**
	 * Import concepts from XML files.
	 * 
	 * @param diagnosisStream
	 * @param mappingStream
	 * @param synonymStream
	 * @return The number of concepts successfully imported into the system
	 * @throws XPathExpressionException
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public Integer importConcepts(InputStream diagnosisStream,
			InputStream mappingStream, InputStream synonymStream)
			throws XPathExpressionException, ParserConfigurationException,
			SAXException, IOException;

	/**
	 * Search patients
	 * 
	 * @param nameOrIdentifier
	 * @param gender
	 * @param age
	 * @param rangeAge
	 * @param date
	 * @param rangeDay
	 * @param relativeName
	 * @return
	 * @throws APIException
	 */
	public List<Patient> searchPatient(String nameOrIdentifier, String gender,
			int age, int rangeAge, String date, int rangeDay,
			String relativeName) throws APIException;

	/**
	 *
	 * @param nameOrIdentifier name of identifier of the person
	 * @param gender gender value to be used for searching the patient
	 * @param age approximate age of the person
	 * @param rangeAge age range to be used for searching the patient based on the approximated age
	 * @param lastDayOfVisit the approximate date the patient was last in a visit
	 * @param lastVisit  range to be used to search the patient, i.e last month, last 6 months or last year or anytime
	 * @param relativeName name of hte patient's relative keyed in during registration
	 * @param maritalStatus marital status of the patient
	 * @param phoneNumber phone number of the patient
	 * @param nationalId national id of the patient
	 * @param fileNumber filenumber of the patient if the patient visits a special clinic
	 * @return list of patients after the filter is executed
	 * @throws APIException
	 */
	public List<Patient> searchPatient(String nameOrIdentifier,String gender,
									   int age , int rangeAge, String lastDayOfVisit, int lastVisit,String relativeName,
									   String maritalStatus,String phoneNumber,String nationalId,String fileNumber) throws APIException;

	/**
	 * Search patients
	 * 
	 * @param hql
	 * @return
	 */
	public List<Patient> searchPatient(String hql);

	/**
	 * Get patient search result count
	 * 
	 * @param hql
	 * @return
	 */
	public BigInteger getPatientSearchResultCount(String hql);

	/**
	 * Get all attributes of an patient
	 * 
	 * @param patientId
	 * @return
	 */
	public List<PersonAttribute> getPersonAttributes(Integer patientId);
	
	/**
	 * Get last visit encounter
	 * @param patient
	 * @param types
	 * @return
	 */
	public Encounter getLastVisitEncounter(Patient patient,
			List<EncounterType> types);

	/**
	 * Save core form
	 * @param form
	 * @return
	 */
	public CoreForm saveCoreForm(CoreForm form);

	/**
	 * Get core form by id
	 * @param id
	 * @return
	 */
	public CoreForm getCoreForm(Integer id);

	/**
	 * Get core forms by name
	 * @param conceptName
	 * @return
	 */
	public List<CoreForm> getCoreForms(String conceptName);

	/**
	 * Get all core forms
	 * @return
	 */
	public List<CoreForm> getCoreForms();

	/**
	 * Delete core form
	 * @param form
	 */
	public void deleteCoreForm(CoreForm form);
	
	/**
	 * Save patientSearch
	 * @param patientSearch
	 * @return
	 */
	public PatientSearch savePatientSearch(PatientSearch patientSearch);
	
	/**
	 * 
	 * get Last Visit time
	 * 
	 * @param patientID
	 * @return
	 */
	public java.util.Date getLastVisitTime (Patient patientID);
	
	//ghanshyam 3-june-2013 New Requirement #1632 Orders from dashboard must be appear in billing queue.User must be able to generate bills from this queue
	public PatientSearch getPatientByPatientId(int patientId);
	public PatientSearch getPatient(int patientID);
	public List<Obs> getObsByEncounterAndConcept(Encounter encounter,Concept concept);
	public PersonAddress getPersonAddress(Person person);
	public OpdTestOrder getOpdTestOrder(Integer opdOrderId);
	public PersonAttributeType getPersonAttributeTypeByName(String attributeName) throws APIException;
	public Obs getObs(Person person,Encounter encounter) throws APIException;
	public String getPatientType(Patient patientId) throws APIException;
	public List<Obs> getObsInstanceForDiagnosis(Encounter encounter,Concept concept) throws APIException;
}
