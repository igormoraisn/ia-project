package ia;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IMember;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.internal.corext.refactoring.code.ExtractMethodRefactoring;
import org.eclipse.jdt.internal.corext.refactoring.structure.PullUpRefactoringProcessor;
import org.eclipse.jdt.internal.corext.refactoring.structure.PushDownRefactoringProcessor;
import org.eclipse.jdt.internal.ui.preferences.JavaPreferencesSettings;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.Refactoring;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.participants.ProcessorBasedRefactoring;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.PlatformUI;

public class RefactoringExecuter {
	private IWorkspace workspace;
	private IWorkspaceRoot root;
	private IProject[] projects;
	public RefactoringExecuter(){
		workspace = ResourcesPlugin.getWorkspace();
		root = workspace.getRoot();
		projects = root.getProjects();
	}
	public void executa(){
		//pullMethod();
		//pullField();
		//pushField();
		pushMethod();
	}
	public IType retornaSuperclass(String mae){
        IType t = null;
        for (IProject project : projects) {
                try {
                    
                	if (project.isNatureEnabled("org.eclipse.jdt.core.javanature")) {
                        IJavaProject javaProject = JavaCore.create(project);
                        IPackageFragment[] packages = javaProject.getPackageFragments();
                        for (IPackageFragment test : packages) {
                        	ICompilationUnit[] uni = test.getCompilationUnits();
                        	for (ICompilationUnit ut : uni) {
                        		IType type[] = ut.getTypes();
                        		for (IType ty : type) {
                        			if(ty.getElementName().compareTo(mae)==0){
                        				t = ty;
                        				break;
                        			}
                        		}
                        	}
                        }
                	}
                }catch (CoreException e) {
                    e.printStackTrace();
            }
        }
        return t;
	}
	public void pullMethod(){
        for (IProject project : projects) {
                try {
                    
                	if (project.isNatureEnabled("org.eclipse.jdt.core.javanature")) {
                        IJavaProject javaProject = JavaCore.create(project);
                        IPackageFragment[] packages = javaProject.getPackageFragments();
                        for (IPackageFragment test : packages) {
                        	ICompilationUnit[] uni = test.getCompilationUnits();
                        	for (ICompilationUnit ut : uni) {
                        		IType type[] = ut.getTypes();
                        		for (IType ty : type) {
                        			String mae = ty.getSuperclassName();
                        			IMethod field[] = ty.getMethods();
                        			for (IMethod fi : field) {
                        				IType mother = retornaSuperclass(mae);
                        				if(doPullMethod(fi, mother)){
                        					break;
                        				}
                        			}
                        		}
                      
                        	}
                        }
                	}
                }catch (CoreException e) {
                    e.printStackTrace();
            }
        }
	}
	public void pushMethod(){
        for (IProject project : projects) {
                try {
                    
                	if (project.isNatureEnabled("org.eclipse.jdt.core.javanature")) {
                        IJavaProject javaProject = JavaCore.create(project);
                        IPackageFragment[] packages = javaProject.getPackageFragments();
                        for (IPackageFragment test : packages) {
                        	ICompilationUnit[] uni = test.getCompilationUnits();
                        	for (ICompilationUnit ut : uni) {
                        		IType type[] = ut.getTypes();
                        		for (IType ty : type) {
                        			IMethod method[] = ty.getMethods();
                        			for (IMethod fi : method) {
                        				if(doPushMethod(fi)){
                        					break;
                        				}
                        			}
                        		}
                      
                        	}
                        }
                	}
                }catch (CoreException e) {
                    e.printStackTrace();
            }
        }
	}
	public boolean doPullMethod(IMethod m, IType type){
		PullUpRefactoringProcessor processor 
		= new PullUpRefactoringProcessor(new IMember[]{m}, 
				JavaPreferencesSettings.getCodeGenerationSettings(m.getJavaProject()));
		Refactoring refactoring = new ProcessorBasedRefactoring(processor);
		processor.setDestinationType(type);
		processor.setMembersToMove(new IMember[]{m});
		processor.setDeletedMethods(new IMethod[]{m});
		try {
		    IProgressMonitor monitor = new NullProgressMonitor();
		    refactoring.checkInitialConditions(monitor);
		    refactoring.checkFinalConditions(monitor);
		    Change change = refactoring.createChange(monitor);
		    change.perform(monitor);

		} catch (CoreException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		} catch (Exception e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}
		return true;
	}
	public void pullField(){
        for (IProject project : projects) {
                try {
                    
                	if (project.isNatureEnabled("org.eclipse.jdt.core.javanature")) {
                        IJavaProject javaProject = JavaCore.create(project);
                        IPackageFragment[] packages = javaProject.getPackageFragments();
                        for (IPackageFragment test : packages) {
                        	ICompilationUnit[] uni = test.getCompilationUnits();
                        	for (ICompilationUnit ut : uni) {
                        		IType type[] = ut.getTypes();
                        		for (IType ty : type) {
                        			String mae = ty.getSuperclassName();
                        			IField field[] = ty.getFields();
                        			for (IField fi : field) {
                        				IType mother = retornaSuperclass(mae);
                        				if(doPullField(fi, mother)){
                        					break;
                        				}
                        			}
                        		}
                      
                        	}
                        }
                	}
                }catch (CoreException e) {
                    e.printStackTrace();
            }
        }
	}
	public void pushField(){
		for (IProject project : projects) {
            try {
                
            	if (project.isNatureEnabled("org.eclipse.jdt.core.javanature")) {
                    IJavaProject javaProject = JavaCore.create(project);
                    IPackageFragment[] packages = javaProject.getPackageFragments();
                    for (IPackageFragment test : packages) {
                    	ICompilationUnit[] uni = test.getCompilationUnits();
                    	for (ICompilationUnit ut : uni) {
                    		IType type[] = ut.getTypes();
                    		for (IType ty : type) {
                    			IField field[] = ty.getFields();
                    			for (IField fi : field) {
                    				if(doPushField(fi)){
                    					break;
                    				}
                    			}
                    		}
                  
                    	}
                    }
            	}
            }catch (CoreException e) {
                e.printStackTrace();
        }
    }
	}
	public boolean doPullField(IField m, IType type){
		PullUpRefactoringProcessor processor 
		= new PullUpRefactoringProcessor(new IMember[]{m}, 
				JavaPreferencesSettings.getCodeGenerationSettings(m.getJavaProject()));
		Refactoring refactoring = new ProcessorBasedRefactoring(processor);
		processor.setDestinationType(type);
		processor.setMembersToMove(new IMember[]{m});
		try {
		    IProgressMonitor monitor = new NullProgressMonitor();
		    refactoring.checkInitialConditions(monitor);
		    refactoring.checkFinalConditions(monitor);
		    Change change = refactoring.createChange(monitor);
		    change.perform(monitor);

		} catch (CoreException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		} catch (Exception e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}
		return true;
	}
	
	public boolean doPushField(IField m){
		PushDownRefactoringProcessor processor 
		= new PushDownRefactoringProcessor(new IMember[]{m});
		Refactoring refactoring = new ProcessorBasedRefactoring(processor);
		try {
		    IProgressMonitor monitor = new NullProgressMonitor();
		    refactoring.checkInitialConditions(monitor);
		    refactoring.checkFinalConditions(monitor);
		    Change change = refactoring.createChange(monitor);
		    change.perform(monitor);

		} catch (CoreException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		} catch (Exception e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}
		return true;
	}
	public boolean doPushMethod(IMethod m){
		PushDownRefactoringProcessor processor 
		= new PushDownRefactoringProcessor(new IMember[]{m});
		Refactoring refactoring = new ProcessorBasedRefactoring(processor);
		try {
		    IProgressMonitor monitor = new NullProgressMonitor();
		    refactoring.checkInitialConditions(monitor);
		    refactoring.checkFinalConditions(monitor);
		    Change change = refactoring.createChange(monitor);
		    change.perform(monitor);

		} catch (CoreException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		} catch (Exception e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}
		return true;
	}
}
