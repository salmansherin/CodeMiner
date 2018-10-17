/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject1;

import com.thoughtworks.qdox.JavaProjectBuilder;
import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.JavaMethod;
import com.thoughtworks.qdox.model.JavaPackage;
import com.thoughtworks.qdox.model.JavaSource;
import java.io.StringReader;
import java.util.HashMap;
import java.util.List;
import org.repodriller.domain.Commit;
import org.repodriller.domain.Modification;
import org.repodriller.persistence.PersistenceMechanism;
import org.repodriller.scm.CommitVisitor;
import org.repodriller.scm.SCMRepository;

public class DevelopersVisitor implements CommitVisitor {

    HashMap files = new HashMap();
    HashMap oldMethodSignatures;

    boolean found;
    String params;
    String diff;

    @Override
    public void process(SCMRepository repo, Commit commit, PersistenceMechanism writer) {
        for (Modification m : commit.getModifications()) {
            diff = "";

            found = false;
            if (!m.fileNameEndsWith(".java")) {
                continue;
            }
            oldMethodSignatures = new HashMap();

            if (m.getType().toString().equals("MODIFY")) {
                try {
                    if (files.containsKey(m.getFileName())) {
                        List<JavaMethod> oldMethods = getMethods(files.get(m.getFileName()).toString());

                        for (JavaMethod method : oldMethods) {
                            oldMethodSignatures.put(method.getName(), method.getParameters().toString());
                        }

                        List<JavaMethod> newMethods = getMethods(m.getSourceCode());

                        for (JavaMethod method : newMethods) {
                            params = oldMethodSignatures.get(method.getName()).toString();
                            if (!params.equals(method.getParameters().toString())) {
                                diff = method.getName();
                                diff += " -> ";
                                diff += params;
                                diff += " | ";
                                diff += method.getParameters().toString();
                                found = true;
                                break;
                            }
                        }

                        if (found) {
                            writer.write(
                                    commit.getHash(),
                                    commit.getCommitter().getName(),
                                    m.getFileName(),
                                    m.getType(),
                                    diff,
                                    m.getDiff()
                            );
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Empty or Invalid Java file");
                }
            }

            files.put(m.getFileName(), m.getSourceCode());

        }

    }

    public List<com.thoughtworks.qdox.model.JavaMethod> getMethods(String source) {
        JavaProjectBuilder builder = new JavaProjectBuilder();
        JavaSource src = builder.addSource(new StringReader(source));
        JavaPackage pkg = src.getPackage();
        JavaClass cls = src.getClasses().get(0);

        return cls.getMethods();
    }

}
