package com.bodastage.e4.clock.ui.internal;

import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Map;

import org.eclipse.jface.resource.FontRegistry;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider.IStyledLabelProvider;
import org.eclipse.jface.viewers.IFontProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;

public class TimeZoneLabelProvider extends LabelProvider 
implements IStyledLabelProvider, IFontProvider {
	private final ISharedImages images;
	private final ImageRegistry ir;
	private final FontRegistry fr;

	@SuppressWarnings("rawtypes")
	public String getText(Object element) {
		if (element instanceof Map) {
			return "Time Zones";
		} else if (element instanceof Map.Entry) {
			return ((Map.Entry) element).getKey().toString();
		} else if (element instanceof ZoneId) {
			return ((ZoneId) element).getId().split("/")[1];
		} else {
			return "Unknown type: " + element.getClass();
		}
	}

	public Image getImage(Object element) {
		if (element instanceof Map.Entry) {
			return images.getImage(ISharedImages.IMG_OBJ_FOLDER);
		} else if (element instanceof ZoneId) {
			return ir.get("sample");
		} else {
			return super.getImage(element);
		}
	}

	public TimeZoneLabelProvider(ISharedImages images, ImageRegistry ir, FontRegistry fr) {
		this.ir = ir;
		this.images = images;
		this.fr = fr;
	}

	@Override
	public StyledString getStyledText(Object element) {
		String text = getText(element);
		StyledString styledString = new StyledString(text);
		if (element instanceof ZoneId) {
			ZoneId zone = (ZoneId) element;
			ZoneOffset offset = ZonedDateTime.now(zone).getOffset();
			styledString.append(" (" + offset + ")", StyledString.DECORATIONS_STYLER);
		}
		return styledString;
	}

	@Override
	public Font getFont(Object element) {
		Font italic = fr.getItalic(JFaceResources.DEFAULT_FONT);
		return italic;
	}
}
