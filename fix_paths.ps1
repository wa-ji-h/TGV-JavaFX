$dir = "C:\Users\ASUS\Downloads\Workshop-JDBC-2526-main (1)\Workshop-JDBC-2526-main\TGV-JavaFX\src\main\java\com\tgv\controllers"
$files = Get-ChildItem -Path $dir -Filter "*Controller.java"

foreach ($file in $files) {
    if ($file.Name -in "ReclamationController.java", "MesReclamationsController.java", "ModifierReclamationController.java", "AdminHomeController.java", "AdminReclamationsController.java", "AdminReponseController.java") {
        $content = Get-Content -Raw -Path $file.FullName -Encoding UTF8
        # Replace occurrences of FXML paths with the new location
        $content = $content.Replace('"/client-home.fxml"', '"/com/tgv/views/client-home.fxml"')
        $content = $content.Replace('"/mes-reclamations.fxml"', '"/com/tgv/views/mes-reclamations.fxml"')
        $content = $content.Replace('"/modifier-reclamation.fxml"', '"/com/tgv/views/modifier-reclamation.fxml"')
        $content = $content.Replace('"/admin-home.fxml"', '"/com/tgv/views/admin-home.fxml"')
        $content = $content.Replace('"/admin-reclamations.fxml"', '"/com/tgv/views/admin-reclamations.fxml"')
        $content = $content.Replace('"/admin-ajouter-reponse.fxml"', '"/com/tgv/views/admin-ajouter-reponse.fxml"')
        Set-Content -Path $file.FullName -Value $content -Encoding UTF8
    }
}
Write-Host "Replaced FXML paths in copied controllers."
