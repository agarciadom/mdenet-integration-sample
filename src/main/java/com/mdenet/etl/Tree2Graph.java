package com.mdenet.etl;

import java.util.Map;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.emfatic.core.EmfaticResourceFactory;
import org.eclipse.epsilon.emc.emf.EmfModel;
import org.eclipse.epsilon.etl.EtlModule;
import org.eclipse.epsilon.flexmi.FlexmiResourceFactory;

public class Tree2Graph {
    public static void main(String[] args) throws Exception {
        // 1. Enable Flexmi and Emfatic support (for this example)
        final Map<String, Object> emap = Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap();
        emap.put("flexmi", new FlexmiResourceFactory());
        emap.put("emf", new EmfaticResourceFactory());

        // 2. Parse the ETL sources
        EtlModule etl = new EtlModule();
        etl.parse(Tree2Graph.class.getResource("/etl/tree2Graph.etl").toURI());
        
        // 3. Add source model (named Source, only for reading)
        EmfModel source = new EmfModel();
        source.setName("Source");
        source.setMetamodelFile("metamodels/tree.emf");
        source.setModelFile("models/tree.flexmi");
        source.setReadOnLoad(true);
        source.setStoredOnDisposal(false);
        source.load();
        etl.getContext().getModelRepository().addModel(source);

        // 4. Add target model (named Target, only for writing)
        EmfModel target = new EmfModel();
        target.setName("Target");
        target.setMetamodelFile("metamodels/graph.emf");
        target.setModelFile("models/graph.xmi");
        target.setReadOnLoad(false);
        target.setStoredOnDisposal(true);
        target.load();
        etl.getContext().getModelRepository().addModel(target);

        // 5. Run the transformation
        try {
            etl.execute();
        } finally {
            etl.getContext().getModelRepository().dispose();
        }

    }
}
