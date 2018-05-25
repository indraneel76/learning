package com.bb.bloomrentalejb.process;

import org.drools.KnowledgeBase;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;

public class KnowledgebaseBuilder {
	public static KnowledgeBase kbase;
	static {
		build();
	}

	private static void build() {
		KnowledgeBuilder kbuilder = KnowledgeBuilderFactory
				.newKnowledgeBuilder();
		kbuilder.add(ResourceFactory
				.newClassPathResource("com.bb.bloomrentalapp.bpmn2"),
				ResourceType.BPMN2);
		kbuilder.add(ResourceFactory.newClassPathResource("app-selection.drl"),
				ResourceType.DRL);
		kbase = kbuilder.newKnowledgeBase();
	}
}
