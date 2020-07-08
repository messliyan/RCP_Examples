
package com.nayaware.dbtools.querybuilder.figures;

import org.eclipse.draw2d.AbstractConnectionAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;

/**
 * Target anchor for the Query builder Join connection
 * 
 * @author Winston Prakash
 * @version 1.0
 */
public class QbJoinTargetAnchor extends AbstractConnectionAnchor {

	public QbJoinTargetAnchor() {
		super();
	}

	public QbJoinTargetAnchor(IFigure owner) {
		super(owner);
	}

	public Point getLocation(Point reference) {

		IFigure parentFigure = getOwner().getParent();

		while (!(parentFigure instanceof QbTableNodeFigure)) {
			parentFigure = parentFigure.getParent();
		}

		Rectangle parentRect = parentFigure.getBounds().getCopy();

		Rectangle ownerRect = getOwner().getBounds().getCopy();
		getOwner().translateToAbsolute(ownerRect);
		Point anchorPoint;
		int off = ownerRect.height / 2;
		if (ownerRect.contains(reference) || ownerRect.right() < reference.x) {
			// return ownerRect.getTopRight().translate(0, off);
			anchorPoint = ownerRect.getTopRight().translate(0, off);
			if (anchorPoint.y > parentRect.getBottomRight().y) {
				anchorPoint.y = parentRect.getBottomRight().y;
			} else if (anchorPoint.y < parentRect.getTopRight().y) {
				anchorPoint.y = parentRect.getTopRight().y;
			}
			anchorPoint.x = parentRect.getBottomRight().x;
		} else {
			// return ownerRect.getTopLeft().translate(0, off);
			anchorPoint = ownerRect.getTopLeft().translate(0, off);
			if (anchorPoint.y < parentRect.getTopLeft().y) {
				anchorPoint.y = parentRect.getTopLeft().y;
			} else if (anchorPoint.y > parentRect.getBottomLeft().y) {
				anchorPoint.y = parentRect.getBottomLeft().y;
			}
			anchorPoint.x = parentRect.getBottomLeft().x;
		}
		return anchorPoint;
	}

}
