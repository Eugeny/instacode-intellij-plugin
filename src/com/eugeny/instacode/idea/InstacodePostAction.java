package com.eugeny.instacode.idea;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;

import java.awt.*;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;

public class InstacodePostAction extends AnAction {
    @Override
    public void update(AnActionEvent event) {
        super.update(event);
        Editor ed = event.getData(PlatformDataKeys.EDITOR);
        VirtualFile f = event.getData(PlatformDataKeys.VIRTUAL_FILE);
        event.getPresentation().setEnabled(!(ed == null || f == null));
    }

    public void actionPerformed(AnActionEvent event) {
        //Project project = event.getData(PlatformDataKeys.PROJECT);
        Editor ed = event.getData(PlatformDataKeys.EDITOR);
        VirtualFile f = event.getData(PlatformDataKeys.VIRTUAL_FILE);

        String selection = ed.getSelectionModel().getSelectedText();

        if (selection == null)
            selection = ed.getDocument().getText();

        try {
            String pCode = URLEncoder.encode(selection, "UTF-8");
            String pLang = URLEncoder.encode(f.getFileType().getName(), "UTF-8").toLowerCase();
            pLang = pLang.substring(0, 1).toUpperCase() + pLang.substring(1);

            String url = "http://instacod.es/?post_code=" + pCode + "&post_lang=" + pLang;
            System.out.println(url);
            Desktop.getDesktop().browse(URI.create(url));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
