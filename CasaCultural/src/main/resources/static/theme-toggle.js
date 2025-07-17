// Função para aplicar tema em elementos carregados dinamicamente
    window.applyCurrentTheme = function() {
        const currentTheme = body.getAttribute('data-theme');
        if (currentTheme === 'dark') {
            applyDarkTheme();
        } else {
            applyLightTheme();
        }
    };
    
    console.log('Sistema de tema carregado com sucesso');

// ===== FUNÇÃO PARA APLICAR TEMA EM ELEMENTOS AJAX =====
function applyThemeToNewElements() {
    const currentTheme = document.body.getAttribute('data-theme');
    const elements = document.querySelectorAll('*:not([data-theme])');
    
    elements.forEach(element => {
        if (!element.classList.contains('theme-toggle')) {
            element.setAttribute('data-theme', currentTheme);
        }
    });
}

// ===== APLICAR TEMA QUANDO PÁGINA CARREGAR =====
window.addEventListener('load', function() {
    const currentTheme = document.body.getAttribute('data-theme');
    if (currentTheme === 'dark') {
        document.body.style.background = '#2d2d2d';
        document.body.style.color = '#ffffff';
    } else {
        document.body.style.background = 'linear-gradient(135deg, #667eea 0%, #764ba2 100%)';
        document.body.style.color = '#333333';
    }
    document.body.style.minHeight = '100vh';
    document.body.style.transition = 'all 0.3s ease';
});