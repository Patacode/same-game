package g56080.same.dp.command;

/**
 * Command interface implemented by executable classes. The interface provide
 * a method to execute the command and to undo it.
 */
public interface Command{

    /**
     * Executes the command.
     */
    public void execute();

    /**
     * Undo the command.
     */
    public void undo();
}
