package io.github.jatoxo;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rtextarea.RTextScrollPane;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class UnifiedView extends Anzeige {
    private CpuDisplay.CpuDisplayPane cpuPane;
    private CpuExtendedDisplay.CpuExtendedDisplayPane cpuExtendedPane;
    private CpuGraphicalDisplay.CpuGraphicalDisplayPane cpuGraphicalPane;
    private SpeicherAnzeige.SpeicherAnzeigePane memoryPane;
    //private List<Editor.EditorPane> editorPanes;

    private JSplitPane editorRestSplit;
    private JSplitPane cpuMemSplit;

    private JTabbedPane editorTabs;



    UnifiedView(ControllerInterface controller, CpuDisplay.CpuDisplayPane cpuPane, CpuExtendedDisplay.CpuExtendedDisplayPane cpuExtendedPane, CpuGraphicalDisplay.CpuGraphicalDisplayPane cpuGraphicalPane, SpeicherAnzeige.SpeicherAnzeigePane memoryPane) {
        super(controller, R.string("appName"));
        this.cpuPane = cpuPane;
        this.cpuExtendedPane = cpuExtendedPane;
        this.cpuGraphicalPane = cpuGraphicalPane;
        this.memoryPane = memoryPane;

        setMinimumSize(new Dimension(200, 200));
    }

    public void addEditor(Editor.EditorPane editor) {
        //if (editorPanes == null) {
        //    editorPanes = new ArrayList<>();
        //}

        //editorPanes.add(editor);

        String name = editor.getFileName();
        editorTabs.addTab(name == null ? "*" + R.string("editor_unsaved") : name, editor);

    }

    @Override
    public JPanel getContent() {
        JPanel content = new JPanel(new BorderLayout());

        editorTabs = new JTabbedPane(SwingConstants.LEFT);

        cpuMemSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        editorRestSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, editorTabs, cpuMemSplit);
        editorRestSplit.setResizeWeight(1);
        content.add(editorRestSplit);


        return content;
    }

    @Override
    protected void initMenus() {
        super.initMenus();

        windowModeToggle.setSelected(true);
    }

    public void activate(JPanel activeCPU, List<Editor.EditorPane> openEditors) {
        //editorTabs.removeAll();

        windowModeToggle.setSelected(true);

        cpuMemSplit.setTopComponent(activeCPU);
        cpuMemSplit.setBottomComponent(memoryPane);

        for(Editor.EditorPane editor : openEditors) {
            addEditor(editor);
        }

        setVisible(true);
    }


    @Override
    protected void resetDisplaySize(boolean increasedSize) {

    }
}
