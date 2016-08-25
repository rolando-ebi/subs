/*
 * Class/Interface name : 
 *
 * Description 			: 
 *
 * Version      		: 1.0
 *
 * Date       			: 
 * 
 * Copyright    		: aoisel
 */

package uk.ac.ebi.subs.validation.checklist;

import uk.ac.ebi.subs.validation.ValidationException;
import uk.ac.ebi.subs.validation.ValidationResult;

/**
 * Use to validate SRA attributes using checklists
 */
public interface FieldValidator {

    /**
     * Validate an SRA object using checklists
     */
    boolean validate(ValidationResult validationResult) throws ValidationException;

    void reset();

}
