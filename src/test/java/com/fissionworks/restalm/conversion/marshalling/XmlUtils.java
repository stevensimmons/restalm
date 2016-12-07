package com.fissionworks.restalm.conversion.marshalling;

import java.io.StringWriter;

import com.fissionworks.restalm.model.customization.EntityField;
import com.fissionworks.restalm.model.customization.EntityFieldCollection;
import com.fissionworks.restalm.model.entity.base.Field;
import com.fissionworks.restalm.model.entity.base.GenericEntity;
import com.fissionworks.restalm.model.entity.base.GenericEntityCollection;
import com.fissionworks.restalm.model.site.Domain;
import com.fissionworks.restalm.model.site.Project;
import com.fissionworks.restalm.model.site.Site;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.CompactWriter;

public final class XmlUtils {

	private XmlUtils() {
	}

	public static final String createEntityCollectionXml(final GenericEntityCollection entities) {
		final StringWriter stringWriter = new StringWriter();
		final HierarchicalStreamWriter writer = new CompactWriter(stringWriter);
		writer.startNode("Entities");
		writer.addAttribute("TotalResults", String.valueOf(entities.getTotalResults()));
		for (final GenericEntity entity : entities) {
			createXml(writer, entity);
		}
		writer.endNode();
		return stringWriter.toString();
	}

	public static final String createEntityFieldCollectionXml(final EntityFieldCollection fieldCollection) {
		final StringWriter stringWriter = new StringWriter();
		final HierarchicalStreamWriter writer = new CompactWriter(stringWriter);
		writer.startNode("Fields");
		for (final EntityField field : fieldCollection) {
			writer.startNode("Field");
			writer.addAttribute("Name", field.getName());
			writer.addAttribute("Label", field.getLabel());
			addFieldNode(writer, "Required", String.valueOf(field.isRequired()));
			addFieldNode(writer, "System", String.valueOf(field.isSystem()));
			addFieldNode(writer, "Editable", String.valueOf(field.isEditable()));
			addFieldNode(writer, "Size", "1");
			addFieldNode(writer, "History", "false");
			addFieldNode(writer, "Type", "String");
			addFieldNode(writer, "Verify", "false");
			addFieldNode(writer, "Virtual", "true");
			addFieldNode(writer, "Active", "false");
			addFieldNode(writer, "Filterable", "true");
			addFieldNode(writer, "Groupable", "false");
			addFieldNode(writer, "SupportsMultivalue", "false");
			writer.endNode();
		}
		writer.endNode();
		return stringWriter.toString();
	}

	public static final String createEntityXml(final GenericEntity entity) {
		final StringWriter stringWriter = new StringWriter();
		final HierarchicalStreamWriter writer = new CompactWriter(stringWriter);
		createXml(writer, entity);
		return stringWriter.toString();
	}

	public static final String createSiteXml(final Site site) {
		final StringWriter stringWriter = new StringWriter();
		final HierarchicalStreamWriter writer = new CompactWriter(stringWriter);
		writer.startNode("Domains");
		for (final Domain domain : site) {
			writer.startNode("Domain");
			writer.addAttribute("Name", domain.getName());
			writer.startNode("Projects");
			for (final Project project : domain.getProjects()) {
				writer.startNode("Project");
				writer.addAttribute("Name", project.getProjectName());
				writer.endNode();
			}
			writer.endNode();
			writer.endNode();
		}
		writer.endNode();

		return stringWriter.toString();
	}

	private static void addFieldNode(final HierarchicalStreamWriter writer, final String nodeName, final String value) {
		writer.startNode(nodeName);
		writer.setValue(value);
		writer.endNode();
	}

	private static void createXml(final HierarchicalStreamWriter writer, final GenericEntity entity) {
		writer.startNode("Entity");
		writer.addAttribute("Type", entity.getType());
		writer.startNode("Fields");
		for (final Field field : entity.getFields()) {
			writer.startNode("Field");
			writer.addAttribute("Name", field.getName());
			if (field.getValues().isEmpty()) {
				writer.startNode("Value");
				writer.endNode();
			} else {
				for (final String value : field.getValues()) {
					writer.startNode("Value");
					writer.setValue(value);
					writer.endNode();
				}
			}
			writer.endNode();
		}
		writer.endNode();
		writer.startNode("RelatedEntities");
		if (entity.hasRelatedEntities()) {
			for (final GenericEntity relatedEntity : entity.getRelatedEntities()) {
				writer.startNode("Relation");
				writer.addAttribute("Alias", relatedEntity.getType());
				createXml(writer, relatedEntity);
				writer.endNode();
			}
		}
		writer.endNode();
		writer.endNode();
	}

}
