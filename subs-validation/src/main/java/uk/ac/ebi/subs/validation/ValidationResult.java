/*******************************************************************************
 * Copyright 2012 EMBL-EBI, Hinxton outstation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package uk.ac.ebi.subs.validation;

import org.apache.commons.lang.builder.ToStringBuilder;

import java.io.IOException;
import java.io.Serializable;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @author dlorenc
 *
 *         A container for validation errors. It also provides additional helper methods.
 */
public class ValidationResult implements Serializable {

    private static final long serialVersionUID = 3511749874894611826L;

    private Collection<ValidationMessage<Origin>> messages;
    private String reportMessage;
    private boolean writeMessageReports = false;//default
    private boolean writeResultReport;

    public ValidationResult() {
        this.messages = new ArrayList<ValidationMessage<Origin>>();
    }

    public String getReportMessage() {
        return reportMessage;
    }

    public void setReportMessage(String reportMessage) {
        this.reportMessage = reportMessage;
    }

    public boolean isHasReportMessage() {
        return reportMessage != null;
    }

    public boolean isWriteMessageReports() {
        return writeMessageReports;
    }

    public void writeMessageReports(boolean writeMessageReports) {
        this.writeMessageReports = writeMessageReports;
    }

    public boolean isWriteResultReport() {
        return writeResultReport;
    }

    public void writeResultReport(boolean writeResultReport) {
        this.writeResultReport = writeResultReport;
    }

    /**
     * Adds a validation message to the result.
     *
     * @param message a validation message to be added
     */
    private void addMessage(ValidationMessage<Origin> message) {
        if (message == null) {
            return;
        }
        this.messages.add(message);
    }

    /**
     * Appends a validation message to the result.
     *
     * @param message a validation message to be added
     * @return a reference to this object
     */
    public ValidationResult append(ValidationMessage<Origin> message) {
        addMessage(message);
        return this;
    }

    /**
     * Appends a collection of validation message to the result.
     *
     * @param messages a collection of validation messages to be added
     * @return a reference to this object
     */
    public ValidationResult append(
            Collection<ValidationMessage<Origin>> messages) {
        if (messages == null) {
            return this;
        }
        for (ValidationMessage<Origin> message : messages) {
            addMessage(message);
        }
        return this;
    }


    /**
     * Appends another validation result (its validation messages) to
     * the result.
     *
     * @param result another validation result
     * @return a reference to this object
     */
    public ValidationResult append(ValidationResult result) {
        if (result == null) {
            return this;
        }
        return append(result.getMessages());
    }

    /**
     * Returns true if no errors have been reported.
     *
     * @return true if no errors have been reported
     */
    public boolean isValid() {
        for (ValidationMessage<Origin> message : messages) {
            if (Severity.ERROR.equals(message.getSeverity())) {
                return false;
            }
        }
        return true;
    }

    public boolean isExtendedResult() {
        return false;
    }

    /**
     * Counts the number of messages.
     */
    public int count() {
        return messages.size();
    }

    /**
     * Counts validation messages by its severity.
     *
     * @param severity a severity of the message which should be counted
     * @return a number of validation messages with provided severity
     */
    public int count(Severity severity) {
        int result = 0;
        if (severity == null) {
            return result;
        }
        for (ValidationMessage<Origin> message : messages) {
            if (severity.equals(message.getSeverity())) {
                result++;
            }
        }
        return result;
    }

    /**
     * Counts validation messages by its severity and message key.
     *
     * @param messageKey a message key of the messages which should be counted
     * @param severity   a severity of the messages which should be counted
     * @return a number of validation messages with provided severity and message key
     */
    public int count(String messageKey, Severity severity) {
        int result = 0;
        if (severity == null || messageKey == null) {
            return result;
        }
        for (ValidationMessage<Origin> message : messages) {
            if (messageKey.equals(message.getMessageKey())
                    && severity.equals(message.getSeverity())) {
                result++;
            }
        }
        return result;
    }

    /**
     * Gets the validation messages.
     *
     * @return unmodifiable collection of all messages
     */
    public Collection<ValidationMessage<Origin>> getMessages() {
        return this.messages;
    }

    /**
     * Finds validation messages by the message key.
     *
     * @param messageKey a message key of the message
     * @return a collection of found validation messages
     */
    public Collection<ValidationMessage<Origin>> getMessages(String messageKey) {

        Collection<ValidationMessage<Origin>> result = new ArrayList<ValidationMessage<Origin>>();

        for (ValidationMessage<Origin> message : messages) {
            if (messageKey.equals(message.getMessageKey())) {
                result.add(message);
            }
        }
        return result;
    }

    /**
     * Finds validation messages by the message key and severity.
     *
     * @param messageKey a message key of the message
     * @param severity   a severity of the message
     * @return a collection of found validation messages
     */
    public Collection<ValidationMessage<Origin>> getMessages(String messageKey, Severity severity) {

        Collection<ValidationMessage<Origin>> result = new ArrayList<ValidationMessage<Origin>>();

        for (ValidationMessage<Origin> message : messages) {
            if (messageKey.equals(message.getMessageKey())
                    && severity.equals(message.getSeverity())) {
                result.add(message);
            }
        }
        return result;
    }


    /**
     * removes all messages with a specified message id
     */
    public void removeMessage(String messageId) {
        Collection<ValidationMessage<Origin>> toRemove = new ArrayList<ValidationMessage<Origin>>();

        for (ValidationMessage<Origin> message : messages) {
            if (messageId.equals(message.getMessageKey())) {
                toRemove.add(message);
            }
        }
        messages.removeAll(toRemove);
    }

    public Collection<ValidationMessage<Origin>> getMessages(Severity severity) {

        Collection<ValidationMessage<Origin>> result = new ArrayList<ValidationMessage<Origin>>();

        for (ValidationMessage<Origin> message : messages) {
            if (severity.equals(message.getSeverity())) {
                result.add(message);
            }
        }
        return result;
    }

    /**
     * Removes all messages
     */
    public void clearMessages() {
        messages.clear();
    }

    @Override
    public String toString() {
        final ToStringBuilder builder = new ToStringBuilder(this);
        builder.append("messages", messages);
        return builder.toString();
    }

    public void writeMessages(Writer writer,
                              Severity severity,
                              String targetOrigin) throws IOException {
        writeMessages(writer, getMessages(severity), targetOrigin);
    }

    public void writeMessages(Writer writer) throws IOException {
        writeMessages(writer, getMessages(), null);
    }

    public void writeMessages(Writer writer, String targetOrigin) throws IOException {
        writeMessages(writer, getMessages(), targetOrigin);
    }

    private void writeMessages(Writer writer,
                               Collection<ValidationMessage<Origin>> messages,
                               String targetOrigin) throws IOException {

        if (!messages.isEmpty()) {
            for (ValidationMessage<Origin> message : messages) {
                writer.write("\n" + message.getSeverity());
                writer.write(": ");
                writer.write(message.getMessage());
//                writer.write(" (" + message.getMessageKey() + ") ");
                for (Object origin : message.getOrigins()) {
                    String originText = ((Origin) origin).getOriginText();
                    writer.write(originText);
                    writer.write(" - " + targetOrigin);
                }

                if (message.isHasCuratorMessage()) {
                    writer.write("\n********\nCurator message: " + message.getCuratorMessage() + "\n********");
                }

                if (writeMessageReports && message.isHasReportMessage()) {
                    writer.write("\n********\nMessage Report:\n\n" + message.getReportMessage() + "END message report********");
                }
                writer.flush();
            }
        }

        if (writeResultReport && isHasReportMessage()) {
            writer.write("\nReport:\n\n" + getReportMessage() + "********\n");
            writer.flush();
        }
    }
}
