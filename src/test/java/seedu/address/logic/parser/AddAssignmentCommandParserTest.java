package seedu.address.logic.parser;

import org.junit.jupiter.api.Test;
import seedu.address.logic.commands.AddAssignmentCommand;
import seedu.address.model.assignment.Assignment;
import seedu.address.model.assignment.Description;
import seedu.address.model.assignment.DueDate;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.TypicalAssignments;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.*;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalPersons.*;

public class AddAssignmentCommandParserTest {
    private AddAssignmentCommandParser parser = new AddAssignmentCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Person expectedPerson = new PersonBuilder(BOB).build();
        Assignment expectedAssignment = TypicalAssignments.ASSIGNMENT_CS1231S_TUTORIAL;
        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB + ASSIGNMENT_DESC_BOB + DATE_DESC_BOB,
                new AddAssignmentCommand(expectedPerson.getName(), expectedAssignment));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddAssignmentCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_BOB + ASSIGNMENT_DESC_BOB + DATE_DESC_BOB,
                expectedMessage);

        // missing assignment description prefix
        assertParseFailure(parser, NAME_DESC_BOB + VALID_DESCRIPTION_CS1231S_TUTORIAL + DATE_DESC_BOB,
                expectedMessage);

        // missing due date prefix
        assertParseFailure(parser, NAME_DESC_BOB + ASSIGNMENT_DESC_BOB + VALID_DATE_TIME_CS1231S_TUTORIAL,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser,
                VALID_NAME_BOB + VALID_DESCRIPTION_CS1231S_TUTORIAL + VALID_DATE_TIME_CS1231S_TUTORIAL,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + ASSIGNMENT_DESC_BOB + DATE_DESC_BOB,
                Name.MESSAGE_CONSTRAINTS);

        // invalid assignment description
        assertParseFailure(parser, NAME_DESC_BOB + INVALID_DESCRIPTION_DESC + DATE_DESC_BOB,
                Description.MESSAGE_CONSTRAINTS);

        // invalid due date
        assertParseFailure(parser, NAME_DESC_BOB + ASSIGNMENT_DESC_BOB + INVALID_DATE_DESCRIPTION,
                DueDate.MESSAGE_CONSTRAINTS_DUE_DATE);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + ASSIGNMENT_DESC_BOB + INVALID_DATE_DESCRIPTION,
                Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB + ASSIGNMENT_DESC_BOB
                        + DATE_DESC_BOB,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddAssignmentCommand.MESSAGE_USAGE));
    }
}