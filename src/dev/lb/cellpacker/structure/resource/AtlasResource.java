package dev.lb.cellpacker.structure.resource;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.tree.DefaultMutableTreeNode;

import dev.lb.cellpacker.Logger;
import dev.lb.cellpacker.annotation.Shortcut;
import dev.lb.cellpacker.annotation.Unmodifiable;
import dev.lb.cellpacker.controls.ControlUtils;
import dev.lb.cellpacker.controls.JImageViewer;

public class AtlasResource extends Resource implements TreeSelectionListener{

	private String imageName;
	private Dimension size;
	private String format;
	private String filter;
	private String repeat;
	private List<Animation> animations;
	
	private JTree animTree;
	private JImageViewer preview;
	private JSplitPane con;
	private JLabel frameDetails;
	
	public AtlasResource(String name, byte[] data) {
		isInitialized = false;
		this.data = data;
		this.name = name;
	}

	public Component createSpriteView(ImageResource main, ImageResource filter){
		JPanel con = new JPanel();
		con.add(new JLabel("Feature not yet supported"));
		return con;
	}
	

	public void init() {
		isInitialized = true;
	}

	@Override
	public Component getComponent() {
		if(isInitialized){
			//return con;
		}else{
			//return ControlUtils.asyncFill(() -> {init(); return con;}, 300);
		}
		return new JLabel("this is not implemented yet");
	}

	@Override
	public Object getContent() {
		if(!isInitialized)
			init();
		return animations;
	}

	@Override
	public Resource clone() {
		return new AtlasResource(getName(), getData());
	}

	@Override
	public FileFilter getFileFilter() {
		return new FileNameExtensionFilter("Atlas file", "*.atlas", ".atlas", "atlas");
	}
	
	public static class Animation implements Iterable<Frame>{
		private List<Frame> frames;
		private String name;
		
		public Animation(String name){
			frames = new ArrayList<>();
			this.name = name;
		}
		
		public boolean isSprite(){
			return frames.size() == 1;
		}
		
		public void addFrame(Frame frame){
			frames.add(frame);
		}
		
		@Unmodifiable
		public List<Frame> getFrames(){
			return Collections.unmodifiableList(frames);
		}

		@Shortcut("getFrames().get(index)")
		public Frame getFrame(int index){
			return getFrames().get(index);
		}
		
		@Override
		@Shortcut("getFrames().iterator()")
		public Iterator<Frame> iterator() {
			return getFrames().iterator();
		}

		public String getName() {
			return name;
		}
	}
	
	public static class Frame{
		private boolean rotate;
		private Point xy;
		private Dimension size;
		private Point orig;
		private Dimension offset;
		private int index;
		
		public Frame(boolean rotate, Point xy, Dimension size, Point orig, Dimension offset, int index) {
			this.rotate = rotate;
			this.xy = xy;
			this.size = size;
			this.orig = orig;
			this.offset = offset;
			this.index = index;
		}

		public Frame(String[] details){
			if(details == null || details.length < 6)
				Logger.throwFatal(new Exception("Error while reading atlas file: invalid frame details").fillInStackTrace());
			this.rotate = Boolean.parseBoolean(details[0]);
			this.xy = parsePoint(details[1]);
			this.size = parseDimension(details[2]);
			this.orig = parsePoint(details[3]);
			this.offset = parseDimension(details[4]);
			this.index = Integer.parseInt(details[5]);
		}
		
		public static Point parsePoint(String string) {
			String first = string.substring(0, string.indexOf(','));
			String last = string.substring(string.indexOf(',') + 1);
			return new Point(Integer.parseInt(first), Integer.parseInt(last));
		}

		public static Dimension parseDimension(String string){
			String first = string.substring(0, string.indexOf(','));
			String last = string.substring(string.indexOf(',') + 1);
			return new Dimension(Integer.parseInt(first), Integer.parseInt(last));
		}
		
		public boolean getRotate() {
			return rotate;
		}

		public Point getXy() {
			return xy;
		}

		public Dimension getSize() {
			return size;
		}

		public Point getOrig() {
			return orig;
		}

		public Dimension getOffset() {
			return offset;
		}

		public int getIndex() {
			return index;
		}
		
		
	}

	public String getImageName() {
		return imageName;
	}

	public Dimension getSize() {
		return size;
	}

	public String getFormat() {
		return format;
	}

	public String getFilter() {
		return filter;
	}

	public String getRepeat() {
		return repeat;
	}

	@Unmodifiable
	public List<Animation> getAnimations() {
		return Collections.unmodifiableList(animations);
	}

	@Override
	public void valueChanged(TreeSelectionEvent e) {
		Object obj = ((DefaultMutableTreeNode) e.getPath().getLastPathComponent()).getUserObject();
		if(obj instanceof String){
			
		}else if(obj instanceof Frame){
			
		}else if(obj instanceof Animation){
			
		}
	}
}
