/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.camel.maven.packaging;

import java.io.File;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.apache.camel.tooling.util.PackageHelper;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

/**
 * Analyses the Camel EIPs in a project and generates extra descriptor
 * information for easier auto-discovery in Camel.
 */
@Mojo(name = "generate-eips-list", threadSafe = true)
public class PackageModelMojo extends AbstractGeneratorMojo {

    /**
     * The camel-core directory
     */
    @Parameter(defaultValue = "${project.build.directory}")
    protected File buildDir;

    /**
     * The output directory for generated models file
     */
    @Parameter(defaultValue = "${project.build.directory}/generated/camel/models")
    protected File outDir;

    /**
     * Execute goal.
     *
     * @throws org.apache.maven.plugin.MojoExecutionException execution of the
     *             main class or one of the threads it generated failed.
     * @throws org.apache.maven.plugin.MojoFailureException something bad
     *             happened...
     */
    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        File camelMetaDir = new File(outDir, "META-INF/services/org/apache/camel/");
        camelMetaDir.mkdirs();

        Set<File> jsonFiles = new TreeSet<>();

        // find all json files in camel-core
        List<String> models = PackageHelper.findJsonFiles(buildDir.toPath().resolve("classes/org/apache/camel/model")).map(p -> p.getFileName().toString())
            // strip out .json from the name
            .map(s -> s.substring(0, s.length() - PackageHelper.JSON_SUFIX.length()))
            // sort
            .sorted().collect(Collectors.toList());

        StringBuilder sb = new StringBuilder();
        sb.append("# " + GENERATED_MSG + NL);
        for (String name : models) {
            sb.append(name).append(NL);
        }

        Path outFile = camelMetaDir.toPath().resolve("model.properties");
        updateResource(outFile, sb.toString());
        getLog().info("Generated " + outFile + " containing " + models.size() + " Camel models");

        addResourceDirectory(outDir.toPath());
    }

}
