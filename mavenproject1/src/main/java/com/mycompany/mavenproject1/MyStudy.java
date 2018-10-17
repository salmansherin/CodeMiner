/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject1;

import org.repodriller.RepoDriller;
import org.repodriller.RepositoryMining;
import org.repodriller.Study;
import org.repodriller.filter.range.Commits;
import org.repodriller.persistence.csv.CSVFile;
import org.repodriller.scm.GitRepository;

public class MyStudy implements Study {

	public static void main(String[] args) {
		new RepoDriller().start(new MyStudy());
	}

	@Override
	public void execute() {
		new RepositoryMining()
		.in(GitRepository.singleProject("/Users/fayazk/Projects/Test/roaster"))
		.through(Commits.all())
		.process(new DevelopersVisitor(), new CSVFile("/Users/fayazk/Desktop/devs.csv"))
		.mine();
	}
}