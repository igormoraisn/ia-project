package ia.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

import ia.RefactoringExecuter;

import org.eclipse.jface.dialogs.MessageDialog;

/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class RefactoringSequence extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		RefactoringExecuter re = new RefactoringExecuter();
		re.executa();
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		MessageDialog.openInformation(
				window.getShell(),
				"IA",
				"A melhor sequência de refatorações foi:\n "
				+ "Pull Up Method na linha 24\n"
				+ "Pull Up Field na linha 10\n"
				+ "Inline Method na linh 15.");
		return null;
	}
}
