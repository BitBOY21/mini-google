async function performSearch() {
    const keyword = document.getElementById('searchInput').value;
    const resultsDiv = document.getElementById('results');

    if (!keyword.trim()) return;

    // Show loading state
    resultsDiv.innerHTML = '<p>Searching indexed pages...</p>';

    try {
        // Send request to our Search Service API
        const response = await fetch(`http://localhost:8083/api/search?keyword=${encodeURIComponent(keyword)}`);

        if (!response.ok) throw new Error('Backend is not responding');

        const data = await response.json();
        resultsDiv.innerHTML = ''; // Clear results

        if (data.length === 0) {
            resultsDiv.innerHTML = '<p>No results found for your query.</p>';
            return;
        }

        // Iterate over each indexed page returned
        data.forEach(page => {
            const item = document.createElement('div');
            item.className = 'result-item';

            // Use the 'highlights' array from Elasticsearch if it exists
            const snippet = (page.highlights && page.highlights.length > 0)
                ? page.highlights.join(' ... ')
                : (page.content.substring(0, 200) + '...');

            item.innerHTML = `
                <span class="result-url">${page.url}</span>
                <a href="${page.url}" target="_blank">${page.title}</a>
                <p class="result-snippet">${snippet}</p>
            `;
            resultsDiv.appendChild(item);
        });

    } catch (error) {
        console.error('Error fetching results:', error);
        resultsDiv.innerHTML = '<p style="color:red;">Error: Make sure Search Service is running on port 8083.</p>';
    }
}

// Allow search by pressing "Enter" key
document.getElementById('searchInput').addEventListener('keypress', (e) => {
    if (e.key === 'Enter') performSearch();
});