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
    private List<Editor.EditorPane> editorPanes;

    private JSplitPane editorRestSplit;
    private JSplitPane cpuMemSplit;



    UnifiedView(ControllerInterface controller, CpuDisplay.CpuDisplayPane cpuPane, CpuExtendedDisplay.CpuExtendedDisplayPane cpuExtendedPane, CpuGraphicalDisplay.CpuGraphicalDisplayPane cpuGraphicalPane, SpeicherAnzeige.SpeicherAnzeigePane memoryPane, List<Editor.EditorPane> editorPanes) {
        super(controller, R.string("appName"));
        this.cpuPane = cpuPane;
        this.cpuExtendedPane = cpuExtendedPane;
        this.cpuGraphicalPane = cpuGraphicalPane;
        this.memoryPane = memoryPane;
        this.editorPanes = editorPanes;

        setMinimumSize(new Dimension(200, 200));
    }

    public void addEditor(Editor.EditorPane editor) {
        if (editorPanes == null) {
            editorPanes = new ArrayList<Editor.EditorPane>();
        }

        editorPanes.add(editor);
    }

    @Override
    public JPanel getContent() {
        JPanel content = new JPanel(new BorderLayout());

        RSyntaxTextArea testSpacer2 = new RSyntaxTextArea();
        RTextScrollPane testSpacer = new RTextScrollPane(testSpacer2);
        testSpacer.setMinimumSize(new Dimension(100, 100));

        cpuMemSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        editorRestSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, testSpacer, cpuMemSplit);
        content.add(editorRestSplit);


        return content;
    }

    public void activate() {
        cpuMemSplit.setTopComponent(cpuGraphicalPane);
        cpuMemSplit.setBottomComponent(memoryPane);

        setVisible(true);
    }


    @Override
    protected void resetDisplaySize(boolean increasedSize) {

    }
}
