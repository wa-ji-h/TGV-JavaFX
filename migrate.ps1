$source_root = "C:\Users\ASUS\Downloads\Workshop-JDBC-2526-main (1)\Workshop-JDBC-2526-main\WorkshopJDBC\src\main"
$target_root = "C:\Users\ASUS\Downloads\Workshop-JDBC-2526-main (1)\Workshop-JDBC-2526-main\TGV-JavaFX\src\main"

$files_to_copy = @(
    @("java\tn\esprit\reclamation\models\Reclamation.java", "java\com\tgv\models\Reclamation.java"),
    @("java\tn\esprit\reclamation\models\Reponse.java", "java\com\tgv\models\Reponse.java"),
    @("java\tn\esprit\reclamation\services\ServiceReclamation.java", "java\com\tgv\services\ReclamationService.java"),
    @("java\tn\esprit\reclamation\services\ServiceReponse.java", "java\com\tgv\services\ReponseService.java"),
    @("java\tn\esprit\reclamation\controllers\ReclamationController.java", "java\com\tgv\controllers\ReclamationController.java"),
    @("java\tn\esprit\reclamation\controllers\MesReclamationsController.java", "java\com\tgv\controllers\MesReclamationsController.java"),
    @("java\tn\esprit\reclamation\controllers\ModifierReclamationController.java", "java\com\tgv\controllers\ModifierReclamationController.java"),
    @("java\tn\esprit\reclamation\controllers\AdminHomeController.java", "java\com\tgv\controllers\AdminHomeController.java"),
    @("java\tn\esprit\reclamation\controllers\AdminReclamationsController.java", "java\com\tgv\controllers\AdminReclamationsController.java"),
    @("java\tn\esprit\reclamation\controllers\AdminAjouterReponseController.java", "java\com\tgv\controllers\AdminReponseController.java"),
    @("resources\reclamation-view.fxml", "resources\com\tgv\views\reclamation-view.fxml"),
    @("resources\mes-reclamations.fxml", "resources\com\tgv\views\mes-reclamations.fxml"),
    @("resources\modifier-reclamation.fxml", "resources\com\tgv\views\modifier-reclamation.fxml"),
    @("resources\admin-home.fxml", "resources\com\tgv\views\admin-home.fxml"),
    @("resources\admin-reclamations.fxml", "resources\com\tgv\views\admin-reclamations.fxml")
)

foreach ($pair in $files_to_copy) {
    $src_rel = $pair[0]
    $tgt_rel = $pair[1]
    $src = Join-Path $source_root $src_rel
    $tgt = Join-Path $target_root $tgt_rel
    
    if (Test-Path $src) {
        $content = Get-Content -Raw -Path $src -Encoding UTF8

        # Class renames
        $content = $content.Replace("ServiceReclamation", "ReclamationService")
        $content = $content.Replace("ServiceReponse", "ReponseService")
        $content = $content.Replace("AdminAjouterReponseController", "AdminReponseController")
        $content = $content.Replace("tn.esprit.utils.MyDatabase", "com.tgv.utils.MyDatabase")

        # Package / imports replacements
        $content = $content.Replace("tn.esprit.reclamation.controllers", "com.tgv.controllers")
        $content = $content.Replace("tn.esprit.reclamation.models", "com.tgv.models")
        $content = $content.Replace("tn.esprit.reclamation.services", "com.tgv.services")
        $content = $content.Replace("tn.esprit.reclamation", "com.tgv")
        
        $tgt_dir = Split-Path $tgt -Parent
        if (-not (Test-Path $tgt_dir)) {
            New-Item -ItemType Directory -Force -Path $tgt_dir | Out-Null
        }
        Set-Content -Path $tgt -Value $content -Encoding UTF8
        Write-Host "Copied and updated $src_rel -> $tgt_rel"
    } else {
        Write-Host "Missing $src"
    }
}
